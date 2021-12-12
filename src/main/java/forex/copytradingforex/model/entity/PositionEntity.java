package forex.copytradingforex.model.entity;

import forex.copytradingforex.model.entity.enums.TradeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@NamedEntityGraph(
        name = "position-comments-trader",
        attributeNodes = {
                @NamedAttributeNode("comments"),
                @NamedAttributeNode("trader")
        }
)
@Entity
@Table(name="positions")
public class PositionEntity extends BaseEntity{

    @ManyToOne
    private EconomicIndicatorEntity economicIndicator;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeEnum trade; //BYU or SELL

   // @Column(nullable = false)
    private LocalDateTime openTime;
    //@Column(nullable = false)
    private LocalDateTime closeTime;

    @Column(columnDefinition = "DECIMAL(19,5)")
    private BigDecimal openPrice;

    @Column(columnDefinition = "DECIMAL(19,5)")
    private BigDecimal closePrice;

    @Column(columnDefinition = "DECIMAL(19,2)")
    private BigDecimal financialResult;

    @Column(columnDefinition = "DECIMAL(19,6)")
    private BigDecimal yield;

    private String videoUrl;

    @ManyToOne
    private UserEntity trader;

    @OneToOne(mappedBy = "position", cascade = CascadeType.REMOVE)
   // @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private PictureEntity picture;


    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    public EconomicIndicatorEntity getEconomicIndicator() {
        return economicIndicator;
    }

    public PositionEntity setEconomicIndicator(EconomicIndicatorEntity economicIndicator) {
        this.economicIndicator = economicIndicator;
        return this;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public PositionEntity setYield(BigDecimal yield) {
        this.yield = yield;
        return this;
    }

    public TradeEnum getTrade() {
        return trade;
    }

    public PositionEntity setTrade(TradeEnum trade) {
        this.trade = trade;
        return this;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public PositionEntity setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
        return this;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public PositionEntity setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public PositionEntity setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public PositionEntity setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
        return this;
    }

    public BigDecimal getFinancialResult() {
        return financialResult;
    }

    public PositionEntity setFinancialResult(BigDecimal result) {
        this.financialResult = result;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public PositionEntity setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public UserEntity getTrader() {
        return trader;
    }

    public PositionEntity setTrader(UserEntity trader) {
        this.trader = trader;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public PositionEntity setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public PositionEntity setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

}
