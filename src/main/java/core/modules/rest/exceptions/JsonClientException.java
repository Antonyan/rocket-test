package core.modules.rest.exceptions;

public final class JsonClientException extends RuntimeException {
    public JsonClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
