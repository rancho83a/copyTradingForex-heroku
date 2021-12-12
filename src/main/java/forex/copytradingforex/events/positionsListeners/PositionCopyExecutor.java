package forex.copytradingforex.events.positionsListeners;


import forex.copytradingforex.events.PositionCreatedEvent;
import forex.copytradingforex.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PositionCopyExecutor {
    private final UserService userService;

    public PositionCopyExecutor(UserService userService) {
        this.userService = userService;
    }

    @EventListener(PositionCreatedEvent.class)
    public  void onPositionCreated(PositionCreatedEvent positionCreatedEvent){

        userService.copyPositionToInvestors(positionCreatedEvent.getTraderUsername(), positionCreatedEvent.getYield());
    }

}
