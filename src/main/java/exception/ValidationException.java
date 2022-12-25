package exception;

public class ValidationException extends IllegalArgumentException {
    public ValidationException(String message) {
        super("Validation failed: " + message);
    }
}
