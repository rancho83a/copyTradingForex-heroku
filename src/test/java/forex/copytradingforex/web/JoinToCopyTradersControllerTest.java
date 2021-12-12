package forex.copytradingforex.web;

import forex.copytradingforex.model.entity.RoleEntity;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.model.entity.enums.RoleEnum;
import forex.copytradingforex.repository.RoleRepository;
import forex.copytradingforex.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WithMockUser("investorTest")
@SpringBootTest
@AutoConfigureMockMvc
class JoinToCopyTradersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String TEST_USERNAME_INVESTOR = "investorTest";
    private static final String TEST_USERNAME_TRADER = "traderToJoinTest";
    private static final BigDecimal CURRENT_CAPITAL = BigDecimal.valueOf(5000);

    private UserEntity investorTest;
    private UserEntity traderToJoin;


    @PostConstruct
    void setUp() {
        RoleEntity investorRole = new RoleEntity().setRole(RoleEnum.INVESTOR);
        roleRepository.save(investorRole);


        investorTest = new UserEntity()
                .setUsername(TEST_USERNAME_INVESTOR)
                .setRoles(List.of(investorRole))
                .setCurrentCapital(CURRENT_CAPITAL)
                .setPassword("12345")
                .setFullName("Test Testov")
                .setEmail("test@test.ts")
                .setAge(33)
                .setExperience(3)
                .setTotalDeposit(BigDecimal.ZERO)
                .setTotalWithdraw(BigDecimal.ZERO)
                .setBufferedAmount(BigDecimal.ZERO);

        investorTest = this.userRepository.save(investorTest);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    @WithUserDetails(value = TEST_USERNAME_INVESTOR)
    @Transactional
    void testJoinToCopyTrader() throws Exception {
        initTraderTojoin();


        mockMvc.perform(get("/traders/" + traderToJoin.getId() + "/copy"))
                .andExpect(status().is3xxRedirection());

        investorTest = userRepository.findByUsername(TEST_USERNAME_INVESTOR).get();
        traderToJoin = userRepository.findByUsername(TEST_USERNAME_TRADER).get();

        List<UserEntity> investors = traderToJoin.getInvestors();
        Assertions.assertEquals(1, investors.size());
        Assertions.assertEquals(investorTest.getFullName(), investors.get(0).getFullName());
        Assertions.assertEquals(traderToJoin.getFullName(), investorTest.getTrader().getFullName());
    }

    private void initTraderTojoin() {
        RoleEntity traderRole = new RoleEntity().setRole(RoleEnum.TRADER);
        roleRepository.save(traderRole);
        traderToJoin = new UserEntity()
                .setUsername(TEST_USERNAME_TRADER)
                .setRoles(List.of(traderRole))
                .setCurrentCapital(CURRENT_CAPITAL)
                .setPassword("12345")
                .setFullName("Test Testov")
                .setEmail("testTrader1@test.ts")
                .setAge(33)
                .setExperience(3)
                .setTotalDeposit(BigDecimal.ZERO)
                .setTotalWithdraw(BigDecimal.ZERO)
                .setBufferedAmount(BigDecimal.ZERO);

        traderToJoin = this.userRepository.save(this.traderToJoin);
    }
}