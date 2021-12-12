package forex.copytradingforex.model.entity;


import forex.copytradingforex.model.entity.enums.CountryEnum;
import forex.copytradingforex.model.entity.enums.CurrencyPairEnum;
import forex.copytradingforex.model.entity.enums.EconomicIndicatorEnum;
import forex.copytradingforex.model.entity.enums.PeriodicityEnum;

import javax.persistence.*;

@Entity
@Table(name="economic_indicators")
public class EconomicIndicatorEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EconomicIndicatorEnum indicator;

    @ManyToOne
    private CountryEntity country;

    //@Column(nullable = false, columnDefinition = "TEXT")
    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PeriodicityEnum periodicity;

    @ManyToOne
    private CurrencyPairEntity currencyPair;

    @ManyToOne
    private TradingRuleEntity tradingRule;


    public PeriodicityEnum getPeriodicity() {
        return periodicity;
    }

    public EconomicIndicatorEntity setPeriodicity(PeriodicityEnum periodicity) {
        this.periodicity = periodicity;
        return this;
    }

    public EconomicIndicatorEnum getIndicator() {
        return indicator;
    }

    public EconomicIndicatorEntity setIndicator(EconomicIndicatorEnum indicator) {
        this.indicator = indicator;
        return this;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public EconomicIndicatorEntity setCountry(CountryEntity country) {
        this.country = country;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EconomicIndicatorEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public CurrencyPairEntity getCurrencyPair() {
        return currencyPair;
    }

    public EconomicIndicatorEntity setCurrencyPair(CurrencyPairEntity currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public TradingRuleEntity getTradingRule() {
        return tradingRule;
    }

    public EconomicIndicatorEntity setTradingRule(TradingRuleEntity tradingRule) {
        this.tradingRule = tradingRule;
        return this;
    }
}
