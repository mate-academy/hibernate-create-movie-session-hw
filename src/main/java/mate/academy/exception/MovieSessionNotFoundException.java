package mate.academy.exception;

public class MovieSessionNotFoundException extends RuntimeException {
    public MovieSessionNotFoundException(String message) {
        super(message);
    }
}
