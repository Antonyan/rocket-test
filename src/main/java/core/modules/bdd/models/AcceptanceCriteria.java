package core.modules.bdd.models;

public class AcceptanceCriteria<T> {

    private T client;

    public AcceptanceCriteria(T client) {
        this.client = client;
    }

    public T given(){
        return client;
    }

    public T when(){
        return client;
    }

    public T then(){
        return client;
    }
}
