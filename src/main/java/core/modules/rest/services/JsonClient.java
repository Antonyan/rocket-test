package core.modules.rest.services;

import core.modules.rest.exceptions.JsonClientException;
import core.modules.rest.models.DomainModel;
import core.modules.rest.models.RequestCollection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Thread safety: fully thread-safe
 */
@Component
public final class JsonClient
{
    private ObjectMapper objectMapper;  //Fully thread-safe

    public JsonClient() {
        setObjectMapper(new ObjectMapper());
    }

    public JsonNode getJsonNode(String json)
    {
        try{
            return getObjectMapper().readTree(json);
        } catch (IOException e){
            throw new JsonClientException("Error getting JsonNode",e);
        }
    }

    public <T> String convertToJson(T domainModel) {
        try{
            return getObjectMapper().writeValueAsString(domainModel);
        } catch (JsonProcessingException e){
            throw new JsonClientException("Error converting domain model to JSON", e);
        }
    }

    public String convertDomainModelToJson(DomainModel domainModel){
        return convertToJson(domainModel);
    }

    public String convertCollectionToJson(RequestCollection requestCollection){
        return convertToJson(requestCollection);
    }

    public JsonNode convertDomainModelToTree(DomainModel domainModel) {
        return getObjectMapper().valueToTree(domainModel);
    }

    public <T extends DomainModel> T convertJsonToDomainModel(String json, Class<T> domainModelType) {
        try{
            getObjectMapper().enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
            getObjectMapper().enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            return getObjectMapper().readValue(json, domainModelType);
        } catch (IOException e){
            throw new JsonClientException("Error converting JSON to domain model", e);
        }
    }

    public <T> ArrayList<T> convertJsonToCollection(String json, Class<T> itemType) {
        try {
            return getObjectMapper().readValue(
                    json,
                    getObjectMapper().getTypeFactory().constructCollectionType(ArrayList.class, itemType)
            );
        } catch (IOException e){
            throw new JsonClientException("Error converting JSON to collection", e);
        }
    }

    private ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private JsonClient setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        return this;
    }
}
