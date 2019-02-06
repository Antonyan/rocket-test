package core.modules.rest.models;

import core.modules.rest.exceptions.ElementDoesntMatchException;
import org.springframework.http.HttpHeaders;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class HttpRequest
{
    private HttpHeaders headers;
    private String server = "";
    private String pathUrl;
    private byte[] body;
    private String method;

    public HttpRequest() {
        setHeaders(new HttpHeaders());
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpRequest setHeaders(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public HttpRequest addHeader(String name, String value){
        getHeaders().add(name,value);
        return this;
    }

    public String getServer() {
        return server;
    }

    public HttpRequest setServer(String server) {
        this.server = server;
        return this;
    }

    public String getBody() {
        return new String(body);
    }
    public HttpRequest setBody(String body) {
        try {
            this.body = null != body ? body.getBytes("UTF-8") : new byte[0];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public byte[] getBodyBytes() { return body; }
    public HttpRequest setBodyBytes(byte[] body) {
        this.body = null != body ? body: new byte[0];
        return this;
    }

    public String getMethod() {
        return method;
    }

    public HttpRequest setMethod(String method) throws ElementDoesntMatchException {
        String[] allowedMethods = {"GET", "PUT", "POST", "DELETE", "PATCH"};

        if (!Arrays.asList(allowedMethods).contains(method)){
            throw new ElementDoesntMatchException(method, allowedMethods);
        }

        this.method = method;
        return this;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public HttpRequest setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
        return this;
    }

    public String getFullUrl() {
        return getServer() + getPathUrl();
    }
}
