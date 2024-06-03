package mate.academy.exception;

import java.util.NoSuchElementException;

public class EntityNotFoundException extends NoSuchElementException {
    public EntityNotFoundException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
