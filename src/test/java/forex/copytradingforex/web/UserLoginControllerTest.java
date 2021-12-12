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
class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;



    private static final String TEST_USERNAME = "traderTest";
    private static final BigDecimal CURRENT_CAPITAL = BigDecimal.valueOf(5000);

    private UserEntity testUser;


    @PostConstruct
    void setUp() {
        RoleEntity traderTest = new RoleEntity().setRole(RoleEnum.TRADER);
        roleRepository.save(traderTest);

        testUser = new UserEntity()
                .setUsername(TEST_USERNAME)
                .setRoles(List.of(traderTest))
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

        testUser = this.userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testOpenLoginPage() throws Exception {
        mockMvc.
                perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }


    @Test
    @WithUserDetails(value = TEST_USERNAME)
    void testOpenProfilePage() throws Exception {
        mockMvc.
                perform(get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userProfile"))
                .andExpect(model().attributeExists("traderCanNotTrade"))
                .andExpect(view().name("profile"));
    }

}