package forex.copytradingforex.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @Column( nullable = false)
    private Boolean approved;

    //@Column(columnDefinition = "TEXT")
    @Lob
    private String textContent;

    @Column( nullable = false)
    private LocalDateTime created;

    @ManyToOne
    private PositionEntity position;

    @ManyToOne
    private UserEntity author;


    public Boolean getApproved() {
        return approved;
    }

    public CommentEntity setApproved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentEntity setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public CommentEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public PositionEntity getPosition() {
        return position;
    }

    public CommentEntity setPosition(PositionEntity position) {
        this.position = position;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }
}
