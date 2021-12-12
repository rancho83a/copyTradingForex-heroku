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
class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String TEST_USERNAME = "masterTest";
    private static final BigDecimal CURRENT_CAPITAL = BigDecimal.TEN;

    private UserEntity masterTest;


    @PostConstruct
    void setUp() {
        RoleEntity masterRole = new RoleEntity().setRole(RoleEnum.MASTER);
        roleRepository.save(masterRole);

        masterTest = new UserEntity()
                .setUsername(TEST_USERNAME)
                .setRoles(List.of(masterRole))
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

        masterTest = this.userRepository.save(masterTest);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }


    @Test
    @WithUserDetails(value = TEST_USERNAME)
    void testOpenStatisticPage() throws Exception {
        mockMvc.
                perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("stats"))
                        .andExpect(view().name("stats"));

    }

}