package forex.copytradingforex.model.service;

public class CommentServiceModel {
    private Long positionId;
    private String textContent;
    private String owner;


    public String getTextContent() {
        return textContent;
    }

    public CommentServiceModel setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public CommentServiceModel setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public Long getPositionId() {
        return positionId;
    }

    public CommentServiceModel setPositionId(Long positionId) {
        this.positionId = positionId;
        return this;
    }
}
