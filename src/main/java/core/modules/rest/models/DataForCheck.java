package core.modules.rest.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.modules.library.models.ColorfulConsole;

import java.util.HashMap;

public class DataForCheck
{
    private HashMap<String,String> data;

    public DataForCheck()
    {
        setData(new HashMap<>());
    }

    public DataForCheck add(String key, String value){
        this.data.put(key, value);
        return this;
    }

    public HashMap<String, String> getData()
    {
        return data;
    }

    @Override
    public String toString() {
        try{
            return new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException exception) {
            new ColorfulConsole().println("Can't convert data to json. Error:" + exception.getMessage(), ColorfulConsole.RED);
        }

        return "";
    }

    private DataForCheck setData(HashMap<String, String> data)
    {
        this.data = data;
        return this;
    }
}
