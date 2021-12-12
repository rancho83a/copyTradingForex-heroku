package forex.copytradingforex.model.binding;

import forex.copytradingforex.model.entity.EconomicIndicatorEntity;
import forex.copytradingforex.model.entity.enums.TradeEnum;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PositionAddBindingModel {


    private Long id;
    @NotNull(message = "can not be empty")
    private Long economicIndicatorId;

    @NotNull(message = "can not be empty")
    private TradeEnum trade; //BYU or SELL

    @NotNull(message = "can not be empty")
    @Past(message = "Date and Time must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime openTime;

    @NotNull(message = "can not be empty")
    @Past(message = "Date and Time must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime closeTime;

    @NotNull(message ="Can not be empty" )
    @Positive(message = "Price must be a positive number")
    private BigDecimal openPrice;

    @NotNull(message ="Can not be empty" )
    @Positive(message = "Price must be a positive number")
    private BigDecimal closePrice;

    @NotNull(message ="Can not be empty" )
    private BigDecimal financialResult;


    @Size( max = 11, message = "VideoUrl must contains 11 symbols")
    private String videoUrl;

    public Long getId() {
        return id;
    }

    public PositionAddBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getEconomicIndicatorId() {
        return economicIndicatorId;
    }

    public PositionAddBindingModel setEconomicIndicatorId(Long economicIndicatorId) {
        this.economicIndicatorId = economicIndicatorId;
        return this;
    }

    public TradeEnum getTrade() {
        return trade;
    }

    public PositionAddBindingModel setTrade(TradeEnum trade) {
        this.trade = trade;
        return this;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public PositionAddBindingModel setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
        return this;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public PositionAddBindingModel setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public PositionAddBindingModel setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public PositionAddBindingModel setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
        return this;
    }

    public BigDecimal getFinancialResult() {
        return financialResult;
    }

    public PositionAddBindingModel setFinancialResult(BigDecimal financialResult) {
        this.financialResult = financialResult;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public PositionAddBindingModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

}
