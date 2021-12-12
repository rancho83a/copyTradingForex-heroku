package forex.copytradingforex.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="fund_history")
public class FundHistoryEntity extends BaseEntity{
    private LocalDateTime created;
    private String operation;
    private BigDecimal amount;
    private BigDecimal currentCapital;
    @ManyToOne
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public FundHistoryEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public FundHistoryEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public FundHistoryEntity setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public FundHistoryEntity setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public BigDecimal getCurrentCapital() {
        return currentCapital;
    }

    public FundHistoryEntity setCurrentCapital(BigDecimal currentCapital) {
        this.currentCapital = currentCapital;
        return this;
    }
}
