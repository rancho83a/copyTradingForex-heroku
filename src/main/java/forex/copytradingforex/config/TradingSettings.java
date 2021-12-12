package forex.copytradingforex.config;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TradingSettings {
    public final static BigDecimal requiredTradingCapital = new BigDecimal(1000);
    public final static BigDecimal requiredCopyCapital = new BigDecimal(100);
    public final static BigDecimal traderRemuneration = new BigDecimal("0.3");
    public final static BigDecimal requiredInvestorCapitalToJoin = new BigDecimal(1000);
    public final static BigDecimal subscriptionFee = new BigDecimal(1);
    //public final static BigDecimal StopLossEquityPercent = new BigDecimal(20);



}
