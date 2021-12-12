package forex.copytradingforex.web;

import forex.copytradingforex.model.entity.*;
import forex.copytradingforex.model.entity.enums.*;
import forex.copytradingforex.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class OpenUpdatePositionPageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private TradingRuleEntityRepository tradingRuleEntityRepository;
    @Autowired
    private CurrencyPairRepository currencyPairRepository;
    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private EconomicIndicatorRepository economicIndicatorRepository;


    private static final String TEST_USERNAME_TRADER = "traderTest";
    private static final BigDecimal CURRENT_CAPITAL = BigDecimal.valueOf(5000);

    private UserEntity traderTest;
    private PositionEntity position;

    @PostConstruct
    void setUp() {
//        currencyRepository.deleteAll();
//        countryRepository.deleteAll();
//        currencyPairRepository.deleteAll();
//        tradingRuleEntityRepository.deleteAll();
//        economicIndicatorRepository.deleteAll();
        positionRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        RoleEntity traderRole = new RoleEntity().setRole(RoleEnum.TRADER);
        roleRepository.save(traderRole);

        traderTest = new UserEntity()
                .setUsername(TEST_USERNAME_TRADER)
                .setRoles(List.of(traderRole))
                .setCurrentCapital(CURRENT_CAPITAL)
                .setPassword("12345")
                .setFullName("Test Testov")
                .setTotalWithdraw(BigDecimal.ZERO)
                .setEmail("test@test.ts")
                .setAge(33)
                .setExperience(3)
                .setTotalDeposit(BigDecimal.ZERO)
                .setTotalWithdraw(BigDecimal.ZERO)
                .setBufferedAmount(BigDecimal.ZERO);

        traderTest = this.userRepository.save(traderTest);
    }

    @AfterEach
    void tearDown() {
        positionRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        currencyRepository.deleteAll();
        currencyPairRepository.deleteAll();
        tradingRuleEntityRepository.deleteAll();
        economicIndicatorRepository.deleteAll();
        countryRepository.deleteAll();

    }

    private static final TradeEnum TRADE = TradeEnum.BUY;
    private static final String DESCRIPTION = "description.........";
    private static final BigDecimal FINANCIAL_RESULT = BigDecimal.TEN;


    @Test
    @WithUserDetails(value = TEST_USERNAME_TRADER)
    @Transactional
    void testUpdatePosition() throws Exception {
        initPosition();

        mockMvc.
                perform(get("/positions/" + position.getId() + "/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("updateBindingModel"))
                .andExpect(view().name("position-update"));
    }



    @Test
    @WithUserDetails(value = TEST_USERNAME_TRADER)
    @Transactional
    void testOpenPositionDetailsPage() throws Exception {
        initPosition();

        mockMvc.
                perform(get("/positions/" + position.getId() + "/details"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("position"))
                .andExpect(view().name("position-details"));
    }


    private void initPosition() {
        CountryEntity country1 = new CountryEntity()
                .setCentralBank(CentralBankEnum.BOE)
                .setFlagUrl("flagUrl")
                .setName(CountryEnum.UK);
        country1 = countryRepository.save(country1);

        CountryEntity country2 = new CountryEntity()
                .setCentralBank(CentralBankEnum.FED)
                .setFlagUrl("flagUrlUSA")
                .setName(CountryEnum.USA);
        country2 = countryRepository.save(country2);

        TradingRuleEntity tradingRule = new TradingRuleEntity()
                .setEntryPoint("entryPointRule")
                .setExitPoint("exitPointRule")
                .setStopLoss(20)
                .setTakeProfit(30);
        tradingRule = tradingRuleEntityRepository.save(tradingRule);

        CurrencyEntity baseCurrency = new CurrencyEntity()
                .setCountry(country1)
                .setCode(CurrencyCodeEnum.GBP)
                .setName("Greate Britain Paund");
        baseCurrency = currencyRepository.save(baseCurrency);

        CurrencyEntity quoteCurrency = new CurrencyEntity()
                .setCountry(country2)
                .setCode(CurrencyCodeEnum.USD)
                .setName("US Dollar");
        quoteCurrency = currencyRepository.save(quoteCurrency);


        CurrencyPairEntity currencyPair = new CurrencyPairEntity()
                .setBaseCurrency(baseCurrency)
                .setQuoteCurrency(quoteCurrency)
                .setName(CurrencyPairEnum.GBPUSD);
        currencyPair = currencyPairRepository.save(currencyPair);


        EconomicIndicatorEntity indicator = new EconomicIndicatorEntity();
        indicator.setIndicator(EconomicIndicatorEnum.CPI)
                .setCountry(country1)
                .setCurrencyPair(currencyPair)
                .setDescription(DESCRIPTION)

                .setPeriodicity(PeriodicityEnum.MONTHLY)
                .setTradingRule(tradingRule);
        EconomicIndicatorEntity economicIndicator = economicIndicatorRepository.save(indicator);

        position = new PositionEntity()
                .setEconomicIndicator(economicIndicator)
                .setTrade(TRADE)
                .setOpenTime(LocalDateTime.now())
                .setCloseTime(LocalDateTime.now())
                .setOpenPrice(BigDecimal.ONE)
                .setClosePrice(BigDecimal.TEN)
                .setFinancialResult(FINANCIAL_RESULT)
                .setTrader(traderTest)
                .setYield(FINANCIAL_RESULT.divide(CURRENT_CAPITAL, 6, RoundingMode.FLOOR));

        position = positionRepository.save(position);
    }


}