package core;

import com.fasterxml.jackson.databind.JsonNode;
import core.modules.library.models.Verbose;
import core.modules.rest.builders.DefaultRequestBuilder;
import core.modules.library.models.Config;
import core.modules.rest.models.HttpHeadersDecorator;
import core.modules.rest.models.HttpRequest;
import core.modules.rest.services.CustomizedEndpointService;
import core.modules.rest.services.EndpointService;
import core.modules.rest.services.HttpRequestService;
import core.modules.rest.services.JsonClient;
import core.modules.rest.services.authStringGenerators.AuthStringGenerator;
import core.modules.rest.services.authStringGenerators.BasicAuthStringGenerator;
import core.modules.rest.services.authStringGenerators.OAuth2StringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpHeaders;

import java.util.prefs.Preferences;

@Configuration
@ComponentScan(basePackages = {"core"})
@Lazy
public class ApiContext
{
    @Autowired
    HttpRequestService httpRequestService;

    @Autowired
    @Qualifier("defaultRestApiHttpRequest")
    HttpRequest defaultRestApiHttpRequest;

    @Autowired
    @Qualifier("defaultRestApiHttpRequestBasicAuth")
    HttpRequest defaultRestApiHttpRequestBasicAuth;

    @Autowired
    Config config;

    @Bean
    public JsonClient jsonClient() {
        return new JsonClient();
    }

    @Bean
    public Config apiConfig() {
        return new Config("api.ini");
    }

    @Bean
    public EndpointService restApiSimple(){
        return new EndpointService(httpRequestService, defaultRestApiHttpRequest, jsonClient());
    }

    @Bean
    public EndpointService restApiBasicAuth(){
        return new EndpointService(httpRequestService, defaultRestApiHttpRequestBasicAuth, jsonClient());
    }

    @Bean
    public Verbose verbose() {
        return new Verbose(config.getPreference().node("General").getBoolean("debug", false));
    }

    @Bean
    public EndpointService restApiOAuth2(){
        return new EndpointService(httpRequestService, defaultRestApiOAuth2HttpRequest(), jsonClient());
    }

    @Bean
    public CustomizedEndpointService restApi(){
        return new CustomizedEndpointService(httpRequestService, defaultRestApiHttpRequest, jsonClient(), apiConfig());
    }

    @Bean
    public AuthStringGenerator basicAuthStringGenerator()
    {
        Preferences config = apiConfig().getPreference().node("RestApiBasicAuth");
        return new BasicAuthStringGenerator(config);
    }

    @Bean
    public AuthStringGenerator basicAuthForOAuth2StringGenerator()
    {
        Preferences config = apiConfig().getPreference().node("OAuth2");
        return new BasicAuthStringGenerator(config);
    }

    @Bean
    public AuthStringGenerator oAuth2StringGenerator()
    {
        return new OAuth2StringGenerator(
                httpRequestService(),
                restApiHttpRequestForHttpAuthorization(),
                new JsonClient(),
                apiConfig().getPreference().node("OAuth2").get("uri", "")
        );
    }

    @Bean
    @Scope("prototype")
    public HttpHeaders defaultRestApiHttpHeadersWithBasicAuth() {
        HttpHeaders httpHeaders = new HttpHeadersDecorator()
                .decorateWithDefaultHeaders(new HttpHeaders(), apiConfig().getPreference());
        httpHeaders.add("Authorization", basicAuthStringGenerator().generate());
        return httpHeaders;
    }

    @Bean
    @Scope("prototype")
    public HttpHeaders defaultRestApiHttpHeaders() {
        return new HttpHeadersDecorator()
                .decorateWithDefaultHeaders(new HttpHeaders(), apiConfig().getPreference());
    }

    @Bean
    @Scope("prototype")
    public HttpRequest defaultRestApiHttpRequest()
    {
        return (new DefaultRequestBuilder()).build(
                apiConfig().getPreference().node("Api").get("baseUrl", ""),
                defaultRestApiHttpHeaders()
        );
    }

    @Bean
    @Scope("prototype")
    public HttpRequest defaultRestApiHttpRequestBasicAuth()
    {
        return (new DefaultRequestBuilder()).build(
                apiConfig().getPreference().node("Api").get("baseUrl", ""),
                defaultRestApiHttpHeadersWithBasicAuth()
        );
    }

    @Bean
    @Scope("prototype")
    public HttpRequest defaultRestApiOAuth2HttpRequest()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "*/*");
        httpHeaders.add("Authorization", oAuth2StringGenerator().generate());

        return (new DefaultRequestBuilder()).build(
                apiConfig().getPreference().node("Api").get("baseUrl", ""),
                httpHeaders
        );
    }

    @Bean
    @Scope("prototype")
    public HttpRequest restApiHttpRequestForHttpAuthorization()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.add("Accept", "*/*");
        httpHeaders.add("Authorization", basicAuthForOAuth2StringGenerator().generate());

        return (new DefaultRequestBuilder()).build(
                apiConfig().getPreference().node("Api").get("baseUrl", ""),
                httpHeaders
        );
    }

    @Bean
    public HttpRequestService httpRequestService()
    {
        return new HttpRequestService(verbose());
    }
}
