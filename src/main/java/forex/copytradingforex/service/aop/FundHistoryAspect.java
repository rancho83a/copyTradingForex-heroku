package forex.copytradingforex.service.aop;

import forex.copytradingforex.model.entity.FundHistoryEntity;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.service.FundHistoryService;
import forex.copytradingforex.service.UserService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class FundHistoryAspect {
    private final UserService userService;
    private BigDecimal capitalBeforeWithdraw;
    private final FundHistoryService fundHistoryService;

    //public static final Logger LOGGER = LoggerFactory.getLogger(FundHistoryAspect.class);

    public FundHistoryAspect(UserService userService, FundHistoryService fundHistoryService) {
        this.userService = userService;
        this.fundHistoryService = fundHistoryService;
    }

    @Pointcut("execution(* forex.copytradingforex.service.impl.UserServiceImpl.depositAmount(..))")
    public void truck() {
    }

    @After(value = "truck() && args(amount,username)", argNames = "amount,username")
    public void afterDepositAddHistoryRecord(BigDecimal amount, String username) {
        UserEntity user = userService.findUserByUsername(username);

        FundHistoryEntity fundHistory = new FundHistoryEntity()
                .setAmount(amount)
                .setCreated(LocalDateTime.now())
                .setOperation("Deposit")
                .setCurrentCapital(user.getCurrentCapital())
                .setUser(user);

        fundHistoryService.save(fundHistory);
    }

    @Pointcut("execution(* forex.copytradingforex.service.impl.UserServiceImpl.withdrawAmount(..))")
    public void truckWithdraw() {
    }


    @Before(value = "truckWithdraw() && args(amount,username)", argNames = "amount,username")
    public void beforeWithdrawAddHistoryRecord(BigDecimal amount, String username) {
        UserEntity user = userService.findUserByUsername(username);
        capitalBeforeWithdraw = user.getCurrentCapital();
    }


    @After(value = "truckWithdraw() && args(amount,username)", argNames = "amount,username")
    public void afterWithdrawAddHistoryRecord(BigDecimal amount, String username) {
        UserEntity user = userService.findUserByUsername(username);

        if (capitalBeforeWithdraw.compareTo(user.getCurrentCapital()) == 0) {
            return;
        }

        FundHistoryEntity fundHistory = new FundHistoryEntity()
                .setAmount(amount)
                .setCreated(LocalDateTime.now())
                .setOperation("Withdraw")
                .setCurrentCapital(user.getCurrentCapital())
                .setUser(user);

        fundHistoryService.save(fundHistory);
   }
}
