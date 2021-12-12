package forex.copytradingforex.model.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PositionDetailsView {
    private Long id;
    private String economicIndicator;
    private String pictureUrl;
    private String trade;
    private BigDecimal yield;
    private String trader;
    private String openTime;
    private String closeTime;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal financialResult;
    private String videoUrl;
    //during mapping from entity to ViewModel set this property for future using in HTML (hide or show buttons DELETE/UPDATE)
    private boolean canDeleteOrUpdate;

    public Long getId() {
        return id;
    }

    public boolean isCanDeleteOrUpdate() {
        return canDeleteOrUpdate;
    }

    public PositionDetailsView setCanDeleteOrUpdate(boolean canDeleteOrUpdate) {
        this.canDeleteOrUpdate = canDeleteOrUpdate;
        return this;
    }

    public PositionDetailsView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEconomicIndicator() {
        return economicIndicator;
    }

    public PositionDetailsView setEconomicIndicator(String economicIndicator) {
        this.economicIndicator = economicIndicator;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public PositionDetailsView setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public String getTrade() {
        return trade;
    }

    public PositionDetailsView setTrade(String trade) {
        this.trade = trade;
        return this;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public PositionDetailsView setYield(BigDecimal yield) {
        this.yield = yield;
        return this;
    }

    public String getTrader() {
        return trader;
    }

    public PositionDetailsView setTrader(String trader) {
        this.trader = trader;
        return this;
    }

    public String getOpenTime() {
        return openTime;
    }

    public PositionDetailsView setOpenTime(String openTime) {
        this.openTime = openTime;
        return this;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public PositionDetailsView setCloseTime(String closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public PositionDetailsView setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public PositionDetailsView setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
        return this;
    }

    public BigDecimal getFinancialResult() {
        return financialResult;
    }

    public PositionDetailsView setFinancialResult(BigDecimal financialResult) {
        this.financialResult = financialResult;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public PositionDetailsView setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }
}
