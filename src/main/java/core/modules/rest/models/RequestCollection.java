package core.modules.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestCollection<T>
{
    private ArrayList<T> items;

    public ArrayList getItems() {
        return items;
    }

    public RequestCollection setItems(ArrayList<T> items) {
        this.items = items;
        return this;
    }

    public RequestCollection addItem(T item){
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
        return this;
    }
}
