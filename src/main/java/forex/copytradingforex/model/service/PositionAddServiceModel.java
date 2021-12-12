package forex.copytradingforex.model.service;

import forex.copytradingforex.model.entity.enums.TradeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PositionAddServiceModel {
    private Long id;

    private Long economicIndicatorId;

    private TradeEnum trade;


    private LocalDateTime openTime;


    private LocalDateTime closeTime;


    private BigDecimal openPrice;


    private BigDecimal closePrice;


    private BigDecimal financialResult;


    private String videoUrl;
    private BigDecimal yield;

    public BigDecimal getYield() {
        return yield;
    }

    public PositionAddServiceModel setYield(BigDecimal yield) {
        this.yield = yield;
        return this;
    }

    public Long getId() {
        return id;
    }

    public PositionAddServiceModel setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getEconomicIndicatorId() {
        return economicIndicatorId;
    }

    public PositionAddServiceModel setEconomicIndicatorId(Long economicIndicatorId) {
        this.economicIndicatorId = economicIndicatorId;
        return this;
    }

    public TradeEnum getTrade() {
        return trade;
    }

    public PositionAddServiceModel setTrade(TradeEnum trade) {
        this.trade = trade;
        return this;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public PositionAddServiceModel setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
        return this;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public PositionAddServiceModel setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public PositionAddServiceModel setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public PositionAddServiceModel setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
        return this;
    }

    public BigDecimal getFinancialResult() {
        return financialResult;
    }

    public PositionAddServiceModel setFinancialResult(BigDecimal financialResult) {
        this.financialResult = financialResult;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public PositionAddServiceModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }
}
