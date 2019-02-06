package core.modules.rest.models;

import org.springframework.http.HttpHeaders;

public class Response
{
    private Integer statusCode = 0;
    private String statusText = "";
    private String responseBody = "";
    private HttpHeaders httpHeaders;

    public Response(Integer statusCode, String statusText, String responseBody, HttpHeaders httpHeaders) {
        setStatusCode(statusCode).setStatusText(statusText).setResponseBody(responseBody).setHttpHeaders(httpHeaders);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    private Response setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusText() {
        return statusText;
    }

    private Response setStatusText(String statusText) {
        this.statusText = statusText;
        return this;
    }

    public String getResponseBody() {
        return responseBody;
    }

    private Response setResponseBody(String responseBody) {
        if (responseBody == null){
            responseBody = "";
        }
        this.responseBody = responseBody;
        return this;
    }

    public String toString(){
        return "Response code: " + getStatusCode().toString() + "\n"
                + "Response headers: " + getHttpHeaders() + "\n"
                + "Response body: " + getResponseBody();
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    private Response setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }
}
