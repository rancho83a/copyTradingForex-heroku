package forex.copytradingforex.model.entity;


import forex.copytradingforex.model.entity.enums.CountryEnum;
import forex.copytradingforex.model.entity.enums.CurrencyCodeEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "currencies")
public class CurrencyEntity extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private CurrencyCodeEnum code;

    @OneToOne(cascade = CascadeType.REMOVE)
    private CountryEntity country;

    public String getName() {
        return name;
    }

    public CurrencyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public CurrencyCodeEnum getCode() {
        return code;
    }

    public CurrencyEntity setCode(CurrencyCodeEnum code) {
        this.code = code;
        return this;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public CurrencyEntity setCountry(CountryEntity country) {
        this.country = country;
        return this;
    }
}
