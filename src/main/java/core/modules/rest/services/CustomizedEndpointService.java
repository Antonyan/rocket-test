package core.modules.rest.services;

import core.modules.library.models.Config;
import core.modules.rest.builders.DefaultRequestBuilder;
import core.modules.rest.models.HttpRequest;
import core.modules.rest.services.authStringGenerators.BasicAuthStringGenerator;
import core.modules.rest.services.authStringGenerators.OAuth2StringGenerator;
import org.springframework.http.HttpHeaders;

public class CustomizedEndpointService extends EndpointService
{
    private Config config;

    public CustomizedEndpointService(
            HttpRequestService httpRequestService,
            HttpRequest httpRequest,
            JsonClient jsonClient,
            Config conf
    )
    {
        super(httpRequestService, httpRequest, jsonClient); setConfig(conf);
    }

    public CustomizedEndpointService useCustomBasicAuth(String user, String password)
    {
        HttpHeaders httpHeaders = basicAuthHeaders(user, password);
        httpHeaders.add("Content-Type", "application/json");
        HttpRequest httpRequest = (new DefaultRequestBuilder()).build(getHttpRequest().getServer(), httpHeaders);
        return new CustomizedEndpointService(getHttpRequestService(), httpRequest, getJsonClient(), getConfig());
    }

    public CustomizedEndpointService useCustomOAuth(String user, String password)
    {
        HttpHeaders httpHeadersWithOAuth = new HttpHeaders();
        httpHeadersWithOAuth.add("Content-Type", "application/json");
        httpHeadersWithOAuth.add("Authorization", getOAth2Token(user, password));

        HttpRequest httpRequestWithOAuth = (new DefaultRequestBuilder()).build(
                getHttpRequest().getServer(),
                httpHeadersWithOAuth
        );

        return new CustomizedEndpointService(getHttpRequestService(), httpRequestWithOAuth, getJsonClient(), getConfig());
    }

    private String getOAth2Token(String user, String password)
    {
        HttpHeaders httpHeaders = basicAuthHeaders(user, password);
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");

        HttpRequest httpRequest = (new DefaultRequestBuilder()).build(
                getConfig().getPreference().node("Servers").get("oAuth2", ""),
                httpHeaders
        );

        return new OAuth2StringGenerator(
                getHttpRequestService(),
                httpRequest,
                getJsonClient(),
                config.getPreference().node("OAuth2").get("uri", "")
        ).generate();
    }

    private HttpHeaders basicAuthHeaders(String user, String password)
    {
        String auth = new BasicAuthStringGenerator(user, password).generate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", auth);
        return httpHeaders;
    }

    private Config getConfig()
    {
        return config;
    }

    private CustomizedEndpointService setConfig(Config config)
    {
        this.config = config;
        return this;
    }
}