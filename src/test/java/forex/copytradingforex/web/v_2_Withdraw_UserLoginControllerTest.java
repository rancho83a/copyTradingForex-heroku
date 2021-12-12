package forex.copytradingforex.web;

import forex.copytradingforex.model.entity.FundHistoryEntity;
import forex.copytradingforex.model.entity.RoleEntity;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.model.entity.enums.RoleEnum;
import forex.copytradingforex.repository.FundHistoryRepository;
import forex.copytradingforex.repository.RoleRepository;
import forex.copytradingforex.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class v_2_Withdraw_UserLoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;



    private static final String TEST_USERNAME = "traderTest";
    private static final BigDecimal CURRENT_CAPITAL = BigDecimal.TEN;
    private static final BigDecimal WITHDRAW_AMOUNT = BigDecimal.ONE;

    private UserEntity testUserWithdraw;


    @PostConstruct
    void setUp() {
        userRepository.deleteAll();
       roleRepository.deleteAll();
        RoleEntity traderTest = new RoleEntity().setRole(RoleEnum.TRADER);
        roleRepository.save(traderTest);

        testUserWithdraw = new UserEntity()
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

        testUserWithdraw = this.userRepository.save(testUserWithdraw);
    }

    @AfterEach
    void tearDown() {
        // AOP Method after deposit -> create records in Fund History, if deleteAll - don`t work aop service
//        userRepository.deleteAll();
//        roleRepository.deleteAll();
    }


    @Test
    @WithUserDetails(value = TEST_USERNAME)
    @Transactional
    void testWithdrawAmount() throws Exception {


        mockMvc.perform(post("/users/withdraw")
                        .param("withdrawAmount", String.valueOf(WITHDRAW_AMOUNT))
                        .with(csrf()).

                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).
                andExpect(status().is3xxRedirection());
        UserEntity actual = userRepository.findByUsername(TEST_USERNAME).get();

        Assertions.assertEquals(BigDecimal.valueOf(9).setScale(2), actual.getCurrentCapital());
        UserEntity user = userRepository.findByUsername(TEST_USERNAME).get();
        FundHistoryEntity fundHistory = user.getFundHistoryRecords().get(0);
        Assertions.assertEquals( WITHDRAW_AMOUNT,fundHistory.getAmount());
        Assertions.assertEquals( "Withdraw",fundHistory.getOperation());
        Assertions.assertEquals( BigDecimal.valueOf(9).setScale(2),fundHistory.getCurrentCapital());
        Assertions.assertEquals( TEST_USERNAME,fundHistory.getUser().getUsername());
    }



}