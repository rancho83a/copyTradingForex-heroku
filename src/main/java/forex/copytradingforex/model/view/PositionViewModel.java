package forex.copytradingforex.model.view;

import forex.copytradingforex.model.entity.enums.EconomicIndicatorEnum;

import java.math.BigDecimal;

public class PositionViewModel {
    private Long id;
    private String economicIndicator;
    private String pictureUrl;
    private String trade;
    private BigDecimal yield;
    private String trader;


    public Long getId() {
        return id;
    }

    public PositionViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEconomicIndicator() {
        return economicIndicator;
    }

    public PositionViewModel setEconomicIndicator(String economicIndicator) {
        this.economicIndicator = economicIndicator;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public PositionViewModel setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public String getTrade() {
        return trade;
    }

    public PositionViewModel setTrade(String trade) {
        this.trade = trade;
        return this;
    }

    public BigDecimal getYield() {
        return yield;
    }


    public PositionViewModel setYield(BigDecimal yield) {
        this.yield = yield;
        return this;
    }

    public String getTrader() {
        return trader;
    }

    public PositionViewModel setTrader(String trader) {
        this.trader = trader;
        return this;
    }
}
