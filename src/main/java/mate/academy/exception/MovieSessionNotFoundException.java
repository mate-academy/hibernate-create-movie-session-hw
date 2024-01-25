package mate.academy.exception;

public class MovieSessionNotFoundException extends RuntimeException {
    public MovieSessionNotFoundException(String message) {
        super(message);
    }

    public MovieSessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
