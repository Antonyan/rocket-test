package core.modules.rest.services;

import core.modules.library.models.Verbose;
import core.modules.rest.models.HttpRequest;
import core.modules.rest.models.Response;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;

public final class HttpRequestService
{
    private RestTemplate rest;
    private Verbose verbose;

    private static final int MAX_CONNECTIONS_PER_ROUTE = 100;
    private static final int MAX_CONNECTIONS_TOTAL = 1000;

    public HttpRequestService(Verbose verbose) {
        createRestTemplate();
        this.verbose = verbose;
    }

    public Response query(HttpRequest httpRequest) {
        verbose.testInfo("REST API Request", request(httpRequest));

        HttpEntity<String> requestEntity = new HttpEntity<String>(httpRequest.getBody(), httpRequest.getHeaders());
        return exchange(httpRequest.getFullUrl(), HttpMethod.valueOf(httpRequest.getMethod()), requestEntity);
    }

    private String request(HttpRequest httpRequest) {
        return  httpRequest.getHeaders() + "\n" +httpRequest.getMethod() + " " + httpRequest.getServer() + httpRequest.getPathUrl() + httpRequest.getBody();
    }

    public Response sendFile(String filePath, HttpRequest httpRequest){
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new FileSystemResource(filePath));
        httpRequest.getHeaders().setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, httpRequest.getHeaders());

        return exchange(httpRequest.getFullUrl(),  HttpMethod.valueOf(httpRequest.getMethod()), requestEntity);
    }

    public Response sendBinaryData(HttpRequest httpRequest) {
        return exchange(
                httpRequest.getFullUrl(),
                HttpMethod.valueOf(httpRequest.getMethod()),
                new HttpEntity<>(
                        httpRequest.getBodyBytes(),
                        httpRequest.getHeaders()
                )
        );
    }

    private Response exchange(String url, HttpMethod httpMethod, HttpEntity httpEntity){
        Response response = null;
        ResponseEntity<String> responseEntity = null;

        try{
            responseEntity = getRest().exchange(url, httpMethod, httpEntity, String.class);
            response = new Response(
                    responseEntity.getStatusCodeValue(),
                    responseEntity.getStatusCode().getReasonPhrase(),
                    responseEntity.getBody(),
                    responseEntity.getHeaders()
            );
        } catch (HttpStatusCodeException e){
            response = new Response(
                    e.getRawStatusCode(),
                    e.getStatusText(),
                    e.getResponseBodyAsString(),
                    e.getResponseHeaders()
            );
        }
        //TODO: look into appropriate solution
        if (httpMethod.matches("DELETE")){
            createRestTemplate();
        }

        verbose.testInfo("Response", response.toString());

        return response;
    }

    private RestTemplate getRest() {
        return rest;
    }

    private HttpRequestService setRest(RestTemplate rest) {
        this.rest = rest;
        return this;
    }

    /**
     * Thread safety:
     * HttpClient is initialized with PoolingHttpClientConnectionManager and is thread-safe
     * RestTemplate is thread-safe once constructed
     */
    private void createRestTemplate(){
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setMaxConnPerRoute(MAX_CONNECTIONS_PER_ROUTE);
        httpClientBuilder.setMaxConnTotal(MAX_CONNECTIONS_TOTAL);
        HttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        setRest(restTemplate);
    }
}
