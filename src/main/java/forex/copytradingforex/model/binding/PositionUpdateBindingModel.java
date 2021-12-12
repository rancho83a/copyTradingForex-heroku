package forex.copytradingforex.model.binding;

import forex.copytradingforex.model.entity.enums.TradeEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PositionUpdateBindingModel {


    private Long id;


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
//
//    @NotNull(message ="Can not be empty" )
//    private BigDecimal financialResult;


    @Size( min=11, max = 11, message = "VideoUrl must contains 11 symbols")
    private String videoUrl;

    public Long getId() {
        return id;
    }

    public PositionUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }


    public TradeEnum getTrade() {
        return trade;
    }

    public PositionUpdateBindingModel setTrade(TradeEnum trade) {
        this.trade = trade;
        return this;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public PositionUpdateBindingModel setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
        return this;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public PositionUpdateBindingModel setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public PositionUpdateBindingModel setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public PositionUpdateBindingModel setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
        return this;
    }

//    public BigDecimal getFinancialResult() {
//        return financialResult;
//    }
//
//    public PositionUpdateBindingModel setFinancialResult(BigDecimal financialResult) {
//        this.financialResult = financialResult;
//        return this;
//    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public PositionUpdateBindingModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }
}
