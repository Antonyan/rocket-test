package core.modules.rest.services;

import core.modules.rest.builders.DefaultRequestBuilder;
import core.modules.rest.exceptions.ElementDoesntMatchException;
import core.modules.rest.models.HttpRequest;
import core.modules.rest.models.Response;
import core.modules.rest.models.Assertion;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.Map;

public class EndpointService
{
    private HttpRequestService httpRequestService;

    private HttpRequest httpRequest;
    private JsonClient jsonClient;

    public EndpointService(HttpRequestService httpRequestService, HttpRequest httpRequest, JsonClient jsonClient)
    {
        setHttpRequestService(httpRequestService).setHttpRequest(httpRequest).setJsonClient(jsonClient);
    }

    public EndpointService addHeader(String name, String value)
    {
        HttpHeaders httpHeaders = copyHttpHeaders();

        httpHeaders.remove(name);
        httpHeaders.add(name, value);
        HttpRequest httpRequest = (new DefaultRequestBuilder()).build(getHttpRequest().getServer(), httpHeaders);
        return new EndpointService(getHttpRequestService(), httpRequest, getJsonClient());
    }

    public Assertion sendGET(String url)
    {
        return new Assertion(query("GET", url, null), getJsonClient());
    }

    public Assertion sendPOST(String url, String json)
    {
        return new Assertion(query("POST", url, json), getJsonClient());
    }

    public Assertion sendPATCH(String url, String json)
    {
        return new Assertion(query("PATCH", url, json), getJsonClient());
    }

    public Assertion sendPUT(String url, String json)
    {
        return new Assertion(query("PUT", url, json), getJsonClient());
    }

    public Assertion sendDELETE(String url)
    {
        return new Assertion(query("DELETE", url, null), getJsonClient());
    }

    private Response query(String method, String url, String json){

        try {
            return getHttpRequestService().query(
                    getHttpRequest()
                            .setMethod(method)
                            .setPathUrl(url)
                            .setBody(json)
            );
        } catch (ElementDoesntMatchException e){
        } catch (HttpStatusCodeException e) {
            return new Response(
                    e.getRawStatusCode(),
                    e.getStatusText(),
                    e.getResponseBodyAsString(),
                    e.getResponseHeaders()
            );
        }
        return new Response(0, "Unexpected exception. EndpointService.query()", "", null);
    }

    protected HttpRequestService getHttpRequestService()
    {
        return httpRequestService;
    }

    protected HttpRequest getHttpRequest()
    {
        return httpRequest;
    }

    protected JsonClient getJsonClient()
    {
        return jsonClient;
    }

    protected EndpointService setHttpRequestService(HttpRequestService httpRequestService)
    {
        this.httpRequestService = httpRequestService;
        return this;
    }

    protected EndpointService setHttpRequest(HttpRequest httpRequest)
    {
        this.httpRequest = httpRequest;
        return this;
    }

    protected EndpointService setJsonClient(JsonClient jsonClient)
    {
        this.jsonClient = jsonClient;
        return this;
    }

    private HttpHeaders copyHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        for(Map.Entry<String, List<String>> entry : httpRequest.getHeaders().entrySet()) {
            httpHeaders.put(entry.getKey(), entry.getValue());
        }
        return httpHeaders;
    }
}