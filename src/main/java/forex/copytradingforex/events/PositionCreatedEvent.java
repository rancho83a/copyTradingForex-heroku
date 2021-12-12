package forex.copytradingforex.events;

import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

public class PositionCreatedEvent extends ApplicationEvent {

    private final String traderUsername;
    private final BigDecimal yield;

    public PositionCreatedEvent(Object source, String traderUsername, BigDecimal yield) {
        super(source);
        this.traderUsername = traderUsername;
        this.yield = yield;
    }

    public String getTraderUsername() {
        return traderUsername;
    }

    public BigDecimal getYield() {
        return yield;
    }
}
