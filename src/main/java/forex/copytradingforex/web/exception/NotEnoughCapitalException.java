package forex.copytradingforex.web.exception;


public class NotEnoughCapitalException extends RuntimeException {
    private final String message;
    public NotEnoughCapitalException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
