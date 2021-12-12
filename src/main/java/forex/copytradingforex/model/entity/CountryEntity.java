package forex.copytradingforex.model.entity;


import forex.copytradingforex.model.entity.enums.CentralBankEnum;
import forex.copytradingforex.model.entity.enums.CountryEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countries")
public class CountryEntity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CountryEnum name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CentralBankEnum centralBank;

    private String flagUrl;

    @OneToMany(mappedBy = "country", cascade = CascadeType.REMOVE)
    private List<EconomicIndicatorEntity> economicIndicators;

    public CountryEnum getName() {
        return name;
    }

    public CountryEntity setName(CountryEnum name) {
        this.name = name;
        return this;
    }

    public CentralBankEnum getCentralBank() {
        return centralBank;
    }

    public CountryEntity setCentralBank(CentralBankEnum centralBank) {
        this.centralBank = centralBank;
        return this;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public CountryEntity setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
        return this;
    }
}
