package forex.copytradingforex.model.entity;

import javax.persistence.*;

@Entity
@Table(name="trading_rules")
public class TradingRuleEntity extends BaseEntity{
    //@Column(nullable = false, columnDefinition = "TEXT")
    @Lob
    private String entryPoint;

    //@Column(nullable = false, columnDefinition = "TEXT")
    @Lob
    private String exitPoint;
    @Column(nullable = false)
    private Integer takeProfit;
    @Column(nullable = false)
    private Integer stopLoss;




    public String getEntryPoint() {
        return entryPoint;
    }

    public TradingRuleEntity setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
        return this;
    }

    public String getExitPoint() {
        return exitPoint;
    }

    public TradingRuleEntity setExitPoint(String exitPoint) {
        this.exitPoint = exitPoint;
        return this;
    }

    public Integer getTakeProfit() {
        return takeProfit;
    }

    public TradingRuleEntity setTakeProfit(Integer takeProfit) {
        this.takeProfit = takeProfit;
        return this;
    }

    public Integer getStopLoss() {
        return stopLoss;
    }

    public TradingRuleEntity setStopLoss(Integer stopLoss) {
        this.stopLoss = stopLoss;
        return this;
    }
}
