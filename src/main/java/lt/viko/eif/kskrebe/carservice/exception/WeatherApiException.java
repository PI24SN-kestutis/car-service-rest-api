package lt.viko.eif.kskrebe.carservice.exception;

import org.springframework.http.HttpStatus;

public class WeatherApiException extends RuntimeException {

    private final HttpStatus status;

    public WeatherApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
