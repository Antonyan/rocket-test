package core.modules.rest.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.testng.Assert;

import java.util.Iterator;
import java.util.Map;

public class NestedList {

    protected boolean includeElementInList(Map<String, String> data, JsonNode itemNode)
    {
        Integer numberOfFields = data.size();
        Integer numberOfMatches = 0;

        for(Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (itemNode.has(key) && itemNode.get(key).asText().equals(value)){
                numberOfMatches++;
            }

            if (numberOfMatches == numberOfFields){
                Assert.assertTrue(true);
                return true;
            }
        }

        return false;
    }

    Boolean includeElementWith(Map<String, String> map, JsonNode jsonNode, String fieldName) {

        if (!jsonNode.has(fieldName)){
            Assert.fail("Response json doesn't contain field: " + fieldName);
        }

        return elementExists(map, jsonNode, fieldName);
    }

    private Boolean elementExists(Map<String, String> map, JsonNode jsonNode, String fieldName) {
        Boolean elementExists = false;
        ArrayNode itemsNode = (ArrayNode) jsonNode.get(fieldName);
        Iterator<JsonNode> itemsIterator = itemsNode.elements();
        while (itemsIterator.hasNext()) {
            JsonNode itemNode = itemsIterator.next();
            if (includeElementInList(map, itemNode)) {
                elementExists = true;
                break;
            }
        }
        return elementExists;
    }
}
