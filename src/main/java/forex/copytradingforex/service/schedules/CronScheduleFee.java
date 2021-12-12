package forex.copytradingforex.service.schedules;

import forex.copytradingforex.config.TradingSettings;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CronScheduleFee {

    public static final Logger LOGGER = LoggerFactory.getLogger(CronScheduleFee.class);
    private final UserService userService;

    public CronScheduleFee(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "${schedulers.subscriptionFeeCron}")
    //@Scheduled(cron = "${schedulers.subscriptionFeeCronDefense}")
    public void paySubscriptionFee() {
        List<UserEntity> tradersWithInvestors = userService.getTradersWithInvestors();
      //  LOGGER.info("Size of Traders with investors: " + tradersWithInvestors.size());
        final UserEntity[] master = {userService.findUserByUsername("master")};

        tradersWithInvestors
                .forEach(trader -> {

                    //if trader has money >= fee
                    if (trader.getCurrentCapital().compareTo(TradingSettings.subscriptionFee) > -1) {

//                        LOGGER.info("Trader`s Capital BEFORE subscription Fee payment: " + trader.getCurrentCapital() + " USD");
//                        LOGGER.info("Master`s Capital BEFORE subscription Fee payment: " + master[0].getCurrentCapital() + " USD");
                        trader.setCurrentCapital(trader.getCurrentCapital().subtract(TradingSettings.subscriptionFee));
                        master[0].setCurrentCapital(master[0].getCurrentCapital().add(TradingSettings.subscriptionFee));
                        UserEntity updatedTrader = userService.save(trader);
                        master[0] = userService.save(master[0]);
//                        LOGGER.info("Trader`s Capital AFTER subscription Fee payment: " + updatedTrader.getCurrentCapital() + " USD");
//                        LOGGER.info("Master`s Capital AFTER subscription Fee payment: " + master[0].getCurrentCapital() + " USD");
                    }
                });
    }

    @Scheduled(cron = "${schedulers.remunerationFeeCron}")
    //@Scheduled(cron = "${schedulers.subscriptionFeeCronDefense1min}")
    public void payRemunerationFee() {
        List<UserEntity> investors = userService.getInvestorDueRemunerationFee();
        //LOGGER.info("Size of Investors Due Remuneration Fee: " + investors.size());


        investors.forEach(investor -> {
            UserEntity trader = investor.getTrader();
//
//            LOGGER.info("Trader`s Capital BEFORE remuneration Fee payment: " + trader.getCurrentCapital() + " USD");
//            LOGGER.info("Investor`s Capital BEFORE remuneration Fee payment: " + investor.getCurrentCapital() + " USD");
//            LOGGER.info("Investor`s BufferedAmount BEFORE remuneration Fee payment: " + investor.getBufferedAmount() + " USD");

            BigDecimal[] data = {investor.getBufferedAmount(), investor.getCurrentCapital(), trader.getCurrentCapital()};

            data = this.userService.remuneration(data);
            investor.setBufferedAmount(data[0]);
            investor.setCurrentCapital(data[1]);
            trader.setCurrentCapital(data[2]);


            trader = userService.save(trader);
            investor = userService.save(investor);

//            LOGGER.info("Trader`s Capital BEFORE remuneration Fee payment: " + trader.getCurrentCapital() + " USD");
//            LOGGER.info("Investor`s Capital BEFORE remuneration Fee payment: " + investor.getCurrentCapital() + " USD");
//            LOGGER.info("Investor`s BufferedAmount BEFORE remuneration Fee payment: " + investor.getBufferedAmount() + " USD");
        });
    }
}
