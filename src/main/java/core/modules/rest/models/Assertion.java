package core.modules.rest.models;

import core.modules.bdd.models.AcceptanceCriteria;
import core.modules.rest.services.JsonClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.HttpHeaders;
import org.testng.Assert;
import java.util.*;

public class Assertion
{
    protected JsonClient jsonClient;
    private Response response;

    public Assertion(Response response, JsonClient jsonClient)
    {
        setResponse(response).setJsonClient(jsonClient);
    }

    public AcceptanceCriteria<Assertion> ac() {
        return new AcceptanceCriteria<>(this);
    }

    public JsonNode getJsonBody()
    {
        return getJsonClient().getJsonNode(getResponse().getResponseBody());
    }

    protected Response getResponseEntity()
    {
        return response;
    }

    public Assertion isHeaderContains(String name, String value){
        Assert.assertEquals(getResponseHeaders().get(name).toString(), "[" + value + "]", "Headers doesn't match");
        return this;
    }

    public Assertion jsonErrorDetailIs(String message) {
        jsonResponseContainsData(
                new HashMap<String,String>(){{
                    put("detail", message);
                }}
        );
        return this;
    }

    public Assertion jsonErrorDetailContainsText(String text) {
        JsonNode responseNode = getJsonBody();
        checkNodeContainsValue("detail", text, responseNode);
        Assert.assertTrue(true);
        return this;
    }


    public Assertion jsonResponseContainsData(Map<String, String> data)
    {
        JsonNode responseNode = getJsonBody();
        checkContainsDataEqualValue(data, responseNode);
        Assert.assertTrue(true);
        return this;
    }

    public Assertion jsonResponseContainsData(JsonNode dataNode)
    {
        JsonNode responseNode = getJsonBody();
        checkContainsDataEqualValue(dataNode, responseNode);
        Assert.assertTrue(true);
        return this;
    }

    public Assertion jsonResponseContainsFields(Collection<String> listOfFields)
    {
        JsonNode responseNode = getJsonBody();
        checkContainsFields(listOfFields, responseNode);
        Assert.assertTrue(true);
        return this;
    }

    public Assertion responseBodyEqual(String body){
        Assert.assertEquals(getResponse().getResponseBody(), body);
        return this;
    }

    public Assertion jsonResponseContainsDataIntValue(Map<String, Integer> data)
    {
        JsonNode responseNode = getJsonBody();
        checkContainsDataIntValue(data, responseNode);
        Assert.assertTrue(true);
        return this;
    }

    public Assertion jsonResponseDoesntContainsData(Map<String, String> data)
    {
        JsonNode responseNode = getJsonBody();
        if (!checkDoesntContainsData(data, responseNode)){
            Assert.fail("Response contains unexpected data");
        }

        Assert.assertTrue(true);
        return this;
    }

    public Assertion responseCodeIs(Integer code)
    {
        Integer responseStatus = getResponseEntity().getStatusCode();
        if (!responseStatus.equals(code)){
            Assert.fail("Response status code is " + responseStatus + " but " + code + " expected.");
        }
        return this;
    }

    public Assertion jsonResponseContainsDataInArray(Map<String, String> data, String field)
    {
        if (!isDataInArray(data, field)){
            Assert.fail("There isn't data in response");
        }
        return this;
    }

    public Assertion jsonResponseDoesntContainDataInArray(Map<String, String> data, String field)
    {
        if (isDataInArray(data, field)){
            Assert.fail("The data is still present in response");
        }
        return this;
    }

    public Assertion jsonResponseContainsDataInCollection(Map<String, String> data)
    {
        if (!isDataInCollection(data)){
            Assert.fail("There isn't data in response");
        }
        return this;
    }

    public Assertion jsonResponseDoesntContainsDataInCollection(Map<String, String> data)
    {
        if (isDataInCollection(data)){
            Assert.fail("Response contains unexpected data in collection");
        }
        return this;
    }

    public Assertion jsonResponseCollectionContainsTotalValue(Integer total)
    {
        Integer currentTotal = getJsonBody().get("totalResults").asInt();
        Assert.assertEquals(currentTotal, total, "A mismatch of items count in the collection");
        return this;
    }

    public Assertion jsonResponseCollectionContainsLimitValue(Integer limit)
    {
        Integer currentLimit = getJsonBody().get("limit").asInt();
        Assert.assertEquals(currentLimit, limit, "A mismatch of limit value in the collection");
        return this;
    }

    public Assertion jsonResponseCollectionContainsOffsetValue(Integer offset)
    {
        Integer currentOffset = getJsonBody().get("offset").asInt();
        Assert.assertEquals(currentOffset, offset, "A mismatch of offset value in the collection");
        return this;
    }

    public Assertion jsonResponseCollectionContainsHasMoreValue(Boolean hasMore)
    {
        Boolean currentOffset = getJsonBody().get("hasMore").asBoolean();
        Assert.assertEquals(currentOffset, hasMore, "A mismatch of hasMore value in the collection.");
        return this;
    }

    public Assertion jsonResponseContainsField(String filedName)
    {
        if (getJsonBody().get(filedName) == null) {
            Assert.fail("The response does't contain field: " + filedName);
        }
        return this;
    }

    public Assertion jsonResponseContainsField(String fieldName, String fieldValue)
    {
        jsonResponseContainsField(fieldName);

        if (!getJsonBody().get(fieldName).asText().equals(fieldValue)) {
            Assert.fail("Response json field: " + fieldName + " = " + getJsonBody().get(fieldName).asText() + " expected " + fieldValue);
        }
        return this;
    }

    public  Assertion jsonResponseContainsSortedDataInCollection(List sortedList, String fieldName){
        if(!isDataSortedInCollection(sortedList, fieldName)){
            Assert.fail("Items are not sorted correctly by " + fieldName);
        }
        return this;
    }

    public Assertion jsonResponseDoesntContainField(String filedName)
    {
        Assert.assertTrue(getJsonBody().get(filedName) == null, "The response contain field: " + filedName);
        return this;
    }

    public Assertion jsonResponseContainsFields(String[] fieldsNames)
    {
        for (String fieldName : fieldsNames){
            jsonResponseContainsField(fieldName);
        }
        return this;
    }

    public Assertion jsonResponseDoesntContainFields(String[] fieldsNames)
    {
        for (String fieldName : fieldsNames){
            jsonResponseDoesntContainField(fieldName);
        }
        return this;
    }

    public Assertion jsonResponseContainsFieldsInCollection(String parentIndex, String[] listOfFields)
    {
        JsonNode responseNode = getJsonBody();
        if (!responseNode.has(parentIndex) || responseNode.get(parentIndex).size() == 0){
            Assert.fail("Response json doesn't contain field: " + parentIndex);
        }

        if (!isFieldsInCollection(responseNode.path(parentIndex), Arrays.asList(listOfFields))){
            Assert.fail("There isn't fields in response");
        }
        return this;
    }

    private Boolean isFieldsInCollection(JsonNode responseNode, Collection<String> listOfFields)
    {
        Iterator<JsonNode> itemsIterator = responseNode.elements();
        while (itemsIterator.hasNext()) {
            JsonNode itemNode = itemsIterator.next();
            checkContainsFields(listOfFields, itemNode);
        }
        return true;
    }

    private Boolean isDataSortedInCollection(List sortedList, String fieldName) {
        ArrayNode itemsNode = (ArrayNode) getJsonBody().get("items");
        Iterator<JsonNode> itemsIterator = itemsNode.elements();

        if(itemsNode.size()!= sortedList.size()){
            Assert.fail("Size of item collection does not match with size of expected test array ");
        }

        while (itemsIterator.hasNext()) {
            JsonNode itemNode = itemsIterator.next();
            if (!checkForEquals(sortedList, itemNode, fieldName)) {
                return false;
            }
            sortedList.remove(0);
        }
        return true;
    }

    private Boolean checkForEquals( List<Object> sortedList, JsonNode itemNode, String fieldName) {

        String itemNodeValue = itemNode.findValue(fieldName).asText();
        String expectedValue = sortedList.get(0).toString();

        if (!itemNodeValue.equals(expectedValue))
            return false;
        return true;
    }

    private Boolean isDataInCollection(Map<String, String> data)
    {
        return new NestedList().includeElementWith(data, getJsonBody(), "items");
    }

    private Boolean isDataInArray (Map<String, String> data, String field)
    {
        return new NestedList().includeElementWith(data, getJsonBody(), field);
    }

    private void checkContainsDataEqualValue(Map<String, String> data, JsonNode responseNode)
    {
        for(Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!responseNode.has(key)){
                Assert.fail("Response json doesn't contain field: " + key);
            }

            if (!responseNode.get(key).asText().equals(value)){
                Assert.fail("Response json field: " + key + " = " + responseNode.get(key).asText() + " expected " + value);
            }
        }
    }

    private void checkNodeContainsValue(String fieldName, String fieldValue, JsonNode responseNode)
    {
        if (!responseNode.has(fieldName)){
            Assert.fail("Response json doesn't contain field: " + fieldName);
        }

        if (!responseNode.get(fieldName).asText().contains(fieldValue)){
            Assert.fail("Response json field: " + fieldName + " = "
                    + responseNode.get(fieldName).asText() + " expected " + fieldValue);
        }
    }

    private void checkContainsFields(Collection<String> listOfFields, JsonNode responseNode)
    {
        for (String fieldName: listOfFields)
        {
            if (!responseNode.has(fieldName)){
                Assert.fail("Response json doesn't contain field: " + fieldName);
            }
        }
    }

    private void checkContainsDataEqualValue(JsonNode dataNode, JsonNode responseNode)
    {
        final Iterator<Map.Entry<String, JsonNode>> iterator = dataNode.fields();
        while (iterator.hasNext()) {
            final Map.Entry<String, JsonNode> dataFieldEntry = iterator.next();
            final String currentFieldName = dataFieldEntry.getKey();
            final JsonNode currentDataNode = dataFieldEntry.getValue();
            final JsonNode matchingResponseNode = responseNode.get(currentFieldName);

            if (!currentDataNode.equals(matchingResponseNode))  //Full (deep) value equality
            {
                Assert.fail(
                        "Response json field: " + currentFieldName +
                                " = " + matchingResponseNode.asText() +
                                " expected " + currentDataNode.asText()
                );
            }
        }
    }

    private void checkContainsDataIntValue(Map<String, Integer> data, JsonNode responseNode)
    {
        for(Map.Entry<String, Integer> entry : data.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            if (!responseNode.has(key)){
                Assert.fail("Response json doesn't contain field: " + key);
            }

            if (responseNode.get(key).asInt() != value){
                Assert.fail("Response json field: " + key + " = " + responseNode.get(key).asText() + " expected " + value);
            }
        }
    }

    private boolean checkDoesntContainsData(Map<String, String> data, JsonNode responseNode)
    {
        for(Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (responseNode.has(key) && responseNode.get(key).asText().equals(value)){
                Assert.fail("Response doesn't have to but contains key: " + key + " value " + value);
                return false;
            }
        }
        return true;
    }

    protected HttpHeaders getResponseHeaders(){
        return getResponseEntity().getHttpHeaders();
    }

    public Response getResponse()
    {
        return response;
    }

    private Assertion setResponse(Response response)
    {
        this.response = response;
        return this;
    }

    private Assertion setJsonClient(JsonClient jsonClient)
    {
        this.jsonClient = jsonClient;
        return this;
    }

    private JsonClient getJsonClient()
    {
        return jsonClient;
    }
}
