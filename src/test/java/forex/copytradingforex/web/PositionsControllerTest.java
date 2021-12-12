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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class PositionsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private PositionRepository positionRepository;

    private static final String TEST_USERNAME_TRADER = "traderTest";
    private static final BigDecimal CURRENT_CAPITAL = BigDecimal.valueOf(5000);

    private UserEntity traderTest;

    @PostConstruct
    void setUp() {

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

        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testOpenPositionsPage() throws Exception {
        mockMvc.
                perform(get("/positions/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("positions"));
    }

    @Test
    @WithUserDetails(value = TEST_USERNAME_TRADER)
    void testOpenAddPositionsPage() throws Exception {
        mockMvc.
                perform(get("/positions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("position-add"));
    }
}