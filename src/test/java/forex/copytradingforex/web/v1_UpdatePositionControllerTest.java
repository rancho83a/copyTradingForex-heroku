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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class v1_UpdatePositionControllerTest {
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
        positionRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        currencyRepository.deleteAll();
        currencyPairRepository.deleteAll();
        tradingRuleEntityRepository.deleteAll();
        economicIndicatorRepository.deleteAll();
        countryRepository.deleteAll();

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
    private static final Long INDICATOR_ID = 1L;


    @Test
    @WithUserDetails(value = TEST_USERNAME_TRADER)
    @Transactional
    void testPatchUpdatePositionVideoUrl() throws Exception {
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
       indicator = economicIndicatorRepository.save(indicator);

        PositionEntity position = new PositionEntity()
                .setEconomicIndicator(indicator)
                .setTrade(TRADE)
                .setOpenTime(LocalDateTime.now())
                .setCloseTime(LocalDateTime.now())
                .setOpenPrice(BigDecimal.ONE)
                .setClosePrice(BigDecimal.TEN)
                .setFinancialResult(FINANCIAL_RESULT)
                .setTrader(traderTest)
                .setYield(FINANCIAL_RESULT.divide(CURRENT_CAPITAL, 6, RoundingMode.FLOOR))
                .setVideoUrl("11111111111");

        position = positionRepository.save(position);

        mockMvc
                .perform(MockMvcRequestBuilders.patch("/positions/" + position.getId() + "/update")
                        .param("economicIndicatorId", String.valueOf(INDICATOR_ID))
                        .param("trade", TRADE.name())
                        .param("openTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
                        .param("closeTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
                        .param("openPrice", String.valueOf(BigDecimal.ONE))
                        .param("closePrice", String.valueOf(BigDecimal.TEN))
                        .param("videoUrl", "12345678901")

                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1L, positionRepository.count());
        Optional<PositionEntity> updatedPositionOpt = positionRepository.findByIdByEntityGraph(1L);
        Assertions.assertTrue(updatedPositionOpt.isPresent());

        PositionEntity newPosition = updatedPositionOpt.get();
        Assertions.assertEquals("12345678901", newPosition.getVideoUrl());
    }
    @Test
    @WithUserDetails(value = TEST_USERNAME_TRADER)
    @Transactional
    void testPatchUpdatePositionWrongVideoUrl() throws Exception {
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
        indicator = economicIndicatorRepository.save(indicator);

        PositionEntity position = new PositionEntity()
                .setEconomicIndicator(indicator)
                .setTrade(TRADE)
                .setOpenTime(LocalDateTime.now())
                .setCloseTime(LocalDateTime.now())
                .setOpenPrice(BigDecimal.ONE)
                .setClosePrice(BigDecimal.TEN)
                .setFinancialResult(FINANCIAL_RESULT)
                .setTrader(traderTest)
                .setYield(FINANCIAL_RESULT.divide(CURRENT_CAPITAL, 6, RoundingMode.FLOOR))
                .setVideoUrl("11111111111");

        position = positionRepository.save(position);

        mockMvc
                .perform(MockMvcRequestBuilders.patch("/positions/" + position.getId() + "/update")
                        .param("economicIndicatorId", String.valueOf(INDICATOR_ID))
                        .param("trade", TRADE.name())
                        .param("openTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
                        .param("closeTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
                        .param("openPrice", String.valueOf(BigDecimal.ONE))
                        .param("closePrice", String.valueOf(BigDecimal.TEN))
                        .param("videoUrl", "1234567890")

                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());
    }
}