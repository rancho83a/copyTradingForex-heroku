package forex.copytradingforex.model.service;

import forex.copytradingforex.model.entity.enums.TradeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PositionUpdateServiceModel {
    private Long id;


    private TradeEnum trade;


    private LocalDateTime openTime;


    private LocalDateTime closeTime;


    private BigDecimal openPrice;


    private BigDecimal closePrice;



    private String videoUrl;

    public Long getId() {
        return id;
    }

    public PositionUpdateServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public TradeEnum getTrade() {
        return trade;
    }

    public PositionUpdateServiceModel setTrade(TradeEnum trade) {
        this.trade = trade;
        return this;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public PositionUpdateServiceModel setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
        return this;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public PositionUpdateServiceModel setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public PositionUpdateServiceModel setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public PositionUpdateServiceModel setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
        return this;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public PositionUpdateServiceModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }
}
