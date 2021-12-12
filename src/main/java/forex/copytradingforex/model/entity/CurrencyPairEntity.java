package forex.copytradingforex.model.entity;


import forex.copytradingforex.model.entity.enums.CurrencyPairEnum;

import javax.persistence.*;

@Entity
@Table(name = "currency_pairs")
public class CurrencyPairEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyPairEnum name;

    @ManyToOne
    private CurrencyEntity baseCurrency;

    @ManyToOne
    private CurrencyEntity quoteCurrency;

    public CurrencyPairEnum getName() {
        return name;
    }

    public CurrencyPairEntity setName(CurrencyPairEnum name) {
        this.name = name;
        return this;
    }

    public CurrencyEntity getBaseCurrency() {
        return baseCurrency;
    }

    public CurrencyPairEntity setBaseCurrency(CurrencyEntity baseCurrency) {
        this.baseCurrency = baseCurrency;
        return this;
    }

    public CurrencyEntity getQuoteCurrency() {
        return quoteCurrency;
    }

    public CurrencyPairEntity setQuoteCurrency(CurrencyEntity quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
        return this;
    }
}
