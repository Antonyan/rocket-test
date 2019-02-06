package core.modules.rest.models;

import org.springframework.http.HttpHeaders;

import java.util.prefs.Preferences;

public class HttpHeadersDecorator {

    public HttpHeaders decorateWithDefaultHeaders(HttpHeaders httpHeaders, Preferences preferences){

        try {
            if (preferences.nodeExists("DefaultHeaders")){
                Preferences headers = preferences.node("DefaultHeaders");
                String[] headersNames = headers.keys();
                for (String headerName: headersNames) {
                    httpHeaders.add(headerName, headers.get(headerName, ""));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Something wrong with default headers");
        }

        return httpHeaders;
    }
}
