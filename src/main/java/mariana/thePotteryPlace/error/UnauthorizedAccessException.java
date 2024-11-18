package mariana.thePotteryPlace.error;

public class UnauthorizedAccessException extends RuntimeException {

    // Constructor that accepts a custom message
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
