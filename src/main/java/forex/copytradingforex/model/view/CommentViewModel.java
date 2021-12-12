package forex.copytradingforex.model.view;

import java.time.LocalDateTime;

public class CommentViewModel {
    private Long id;
    private String textContent;
    private String owner;
    private String created;
    private boolean  canApprove;
    private boolean canDelete;

    public Long getId() {
        return id;
    }

    public CommentViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentViewModel setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public CommentViewModel setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public CommentViewModel setCreated(String created) {
        this.created = created;
        return this;
    }

    public boolean isCanApprove() {
        return canApprove;
    }

    public CommentViewModel setCanApprove(boolean canApprove) {
        this.canApprove = canApprove;
        return this;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public CommentViewModel setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
        return this;
    }
}
