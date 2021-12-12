package forex.copytradingforex.web.exception;

public class PositionNotFoundException extends RuntimeException {
    private final Long positionId;

    public PositionNotFoundException(Long positionId) {
        super("Position with id " + positionId + " was not found");
        this.positionId = positionId;
    }

    public Long getPositionId() {
        return positionId;
    }
}
