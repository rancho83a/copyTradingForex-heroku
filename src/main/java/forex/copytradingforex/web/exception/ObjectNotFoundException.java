package forex.copytradingforex.web.exception;

public class ObjectNotFoundException extends RuntimeException {
    private final Long objectId;

    public ObjectNotFoundException(Long objectId) {
        super("Can not find object with id "+ objectId);
        this.objectId = objectId;
    }

    public Long getObjectId() {
        return objectId;
    }


}
