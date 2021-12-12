package forex.copytradingforex.web;

import forex.copytradingforex.model.entity.RoleEntity;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.model.entity.enums.RoleEnum;
import forex.copytradingforex.repository.RoleRepository;
import forex.copytradingforex.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TradersControllerTest {
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
    private RoleEntity traderRole;

    @PostConstruct
    void setUp() {
        RoleEntity investorRole = new RoleEntity().setRole(RoleEnum.INVESTOR);
        traderRole = new RoleEntity().setRole(RoleEnum.TRADER);
        roleRepository.save(investorRole);
        roleRepository.save(traderRole);

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
    void testOpenTradersPage() throws Exception {
        mockMvc.
                perform(get("/traders/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("traders"))
                .andExpect(view().name("traders"));
    }
}