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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AddPositionsControllerTest {
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

    private static final Long INDICATOR_ID = 1L;
    private static final TradeEnum TRADE = TradeEnum.BUY;
    private static final String DESCRIPTION  = "description.........";
    private static final BigDecimal FINANCIAL_RESULT = BigDecimal.TEN;


    @Test
    @WithUserDetails(value = TEST_USERNAME_TRADER)
    @Transactional
    void testAddPosition() throws Exception {
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
         tradingRule= tradingRuleEntityRepository.save(tradingRule);

         CurrencyEntity baseCurrency=new CurrencyEntity()
                 .setCountry(country1)
                 .setCode(CurrencyCodeEnum.GBP)
                 .setName("Greate Britain Paund");
         baseCurrency=currencyRepository.save(baseCurrency);

        CurrencyEntity quoteCurrency=new CurrencyEntity()
                .setCountry(country2)
                .setCode(CurrencyCodeEnum.USD)
                .setName("US Dollar");
        quoteCurrency=currencyRepository.save(quoteCurrency);


         CurrencyPairEntity currencyPair =new CurrencyPairEntity()
                 .setBaseCurrency(baseCurrency)
                 .setQuoteCurrency(quoteCurrency)
                 .setName(CurrencyPairEnum.GBPUSD);
         currencyPair=currencyPairRepository.save(currencyPair);



        EconomicIndicatorEntity indicator = new EconomicIndicatorEntity();
        indicator.setIndicator(EconomicIndicatorEnum.CPI)
                .setCountry(country1)
                .setCurrencyPair(currencyPair)
                .setDescription(DESCRIPTION)

                .setPeriodicity(PeriodicityEnum.MONTHLY)
                .setTradingRule(tradingRule);
        indicator = economicIndicatorRepository.save(indicator);

        mockMvc.
                perform(post("/positions/add")
                        .param("economicIndicatorId", String.valueOf(INDICATOR_ID))
                        .param("trade", TRADE.name())
                        .param("openTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
                        .param("closeTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
                        .param("openPrice", String.valueOf(BigDecimal.ONE))
                        .param("closePrice", String.valueOf(BigDecimal.TEN))
                        .param("financialResult",String.valueOf(FINANCIAL_RESULT))

                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1L, positionRepository.count());
        Optional<PositionEntity> newCreatedPositionOpt = positionRepository.findByIdByEntityGraph(1L);
        Assertions.assertTrue(newCreatedPositionOpt.isPresent());

        PositionEntity newPosition = newCreatedPositionOpt.get();
        Assertions.assertEquals(TEST_USERNAME_TRADER, newPosition.getTrader().getUsername());
        Assertions.assertNull(newPosition.getPicture());

        Assertions.assertEquals(FINANCIAL_RESULT, newPosition.getFinancialResult());
        Assertions.assertEquals(CountryEnum.UK, newPosition.getEconomicIndicator().getCountry().getName());
        Assertions.assertEquals(DESCRIPTION, newPosition.getEconomicIndicator().getDescription());
        Assertions.assertEquals(PeriodicityEnum.MONTHLY, newPosition.getEconomicIndicator().getPeriodicity());
        Assertions.assertEquals(EconomicIndicatorEnum.CPI, newPosition.getEconomicIndicator().getIndicator());
        Assertions.assertEquals("entryPointRule", newPosition.getEconomicIndicator().getTradingRule().getEntryPoint());
        Assertions.assertEquals("exitPointRule", newPosition.getEconomicIndicator().getTradingRule().getExitPoint());
        Assertions.assertEquals(30, newPosition.getEconomicIndicator().getTradingRule().getTakeProfit());
        Assertions.assertEquals(20, newPosition.getEconomicIndicator().getTradingRule().getStopLoss());
        Assertions.assertEquals(CurrencyCodeEnum.GBP, newPosition.getEconomicIndicator().getCurrencyPair().getBaseCurrency().getCode());
        Assertions.assertEquals(CurrencyCodeEnum.USD, newPosition.getEconomicIndicator().getCurrencyPair().getQuoteCurrency().getCode());
        Assertions.assertEquals("US Dollar", newPosition.getEconomicIndicator().getCurrencyPair().getQuoteCurrency().getName());
        Assertions.assertEquals(CentralBankEnum.FED , newPosition.getEconomicIndicator().getCurrencyPair().getQuoteCurrency().getCountry().getCentralBank());
    }


    @Test
    @WithUserDetails(value = TEST_USERNAME_TRADER)
    void testWrongOpenTimeAddPosition() throws Exception {

        mockMvc.
                perform(post("/positions/add")
                        .param("economicIndicatorId", String.valueOf(INDICATOR_ID))
                        .param("trade", TRADE.name())
                        .param("openTime", LocalDateTime.now().toString())
                        .param("closeTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
                        .param("openPrice", String.valueOf(BigDecimal.ONE))
                        .param("closePrice", String.valueOf(BigDecimal.TEN))
                        .param("financialResult", String.valueOf(BigDecimal.valueOf(1000)))

                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());
    }


}