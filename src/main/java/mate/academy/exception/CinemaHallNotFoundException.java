package mate.academy.exception;

public class CinemaHallNotFoundException extends RuntimeException {
    public CinemaHallNotFoundException(String message) {
        super(message);
    }

    public CinemaHallNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
