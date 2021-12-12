package forex.copytradingforex.model.view;

import forex.copytradingforex.model.entity.TradingRuleEntity;
import forex.copytradingforex.model.entity.enums.EconomicIndicatorEnum;
import forex.copytradingforex.model.entity.enums.PeriodicityEnum;

public class EconomicIndicatorViewModel {
    private Long id;
    private String indicator;

    private String country;
    private String currencyPair;
    private String description;
    private PeriodicityEnum periodicity;
    private String entryPoint;
    private String exitPoint;
    private Integer takeProfit;
    private Integer stopLoss;

    public Long getId() {
        return id;
    }

    public EconomicIndicatorViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public EconomicIndicatorViewModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public EconomicIndicatorViewModel setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EconomicIndicatorViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PeriodicityEnum getPeriodicity() {
        return periodicity;
    }

    public EconomicIndicatorViewModel setPeriodicity(PeriodicityEnum periodicity) {
        this.periodicity = periodicity;
        return this;
    }

    public String getEntryPoint() {
        return entryPoint;
    }

    public EconomicIndicatorViewModel setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
        return this;
    }

    public String getExitPoint() {
        return exitPoint;
    }

    public EconomicIndicatorViewModel setExitPoint(String exitPoint) {
        this.exitPoint = exitPoint;
        return this;
    }

    public Integer getTakeProfit() {
        return takeProfit;
    }

    public EconomicIndicatorViewModel setTakeProfit(Integer takeProfit) {
        this.takeProfit = takeProfit;
        return this;
    }

    public Integer getStopLoss() {
        return stopLoss;
    }

    public EconomicIndicatorViewModel setStopLoss(Integer stopLoss) {
        this.stopLoss = stopLoss;
        return this;
    }

    public String getIndicator() {
        return indicator;
    }

    public EconomicIndicatorViewModel setIndicator(String indicator) {
        this.indicator = indicator;
        return this;
    }

}
