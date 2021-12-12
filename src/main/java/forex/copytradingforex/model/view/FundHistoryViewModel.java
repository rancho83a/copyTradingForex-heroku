package forex.copytradingforex.model.view;

import forex.copytradingforex.model.entity.UserEntity;

import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FundHistoryViewModel {
    private Long id;
    private String created;
    private String operation;
    private BigDecimal amount;
    private BigDecimal currentCapital;
    private String user;

    public Long getId() {
        return id;
    }

    public FundHistoryViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public FundHistoryViewModel setCreated(String created) {
        this.created = created;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public FundHistoryViewModel setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public FundHistoryViewModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public BigDecimal getCurrentCapital() {
        return currentCapital;
    }

    public FundHistoryViewModel setCurrentCapital(BigDecimal currentCapital) {
        this.currentCapital = currentCapital;
        return this;
    }

    public String getUser() {
        return user;
    }

    public FundHistoryViewModel setUser(String user) {
        this.user = user;
        return this;
    }
}
