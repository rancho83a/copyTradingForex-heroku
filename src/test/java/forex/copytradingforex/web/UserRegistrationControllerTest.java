package forex.copytradingforex.web;

import forex.copytradingforex.model.entity.RoleEntity;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.model.entity.enums.RoleEnum;
import forex.copytradingforex.repository.FundHistoryRepository;
import forex.copytradingforex.repository.RoleRepository;
import forex.copytradingforex.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRegistrationControllerTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testOpenRegisterForm() throws Exception {
        mockMvc.
                perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    private static final String TEST_USERNAME = "testTraderRegister";
    private static final String TEST_USER_EMAIL = "trader_test@test.com";
    private static final String TEST_FULL_NAME = "Test Testov";
    private static final int TEST_USER_AGE = 33;
    private static final String TEST_IMAGE_URL = "http://somepath.com/pic.jpg";
    private static final BigDecimal CURRENT_CAPITAL = BigDecimal.TEN;
    private static final int TEST_EXPERIENCE = 10;
    private static final Long ROLE_ID = 1L;

    @Test
    void testRegisterUser() throws Exception {
        RoleEntity trader = new RoleEntity();
        trader.setRole(RoleEnum.TRADER);
        roleRepository.save(trader);

        mockMvc.perform(post("/users/register")
                        .param("username", TEST_USERNAME)
                        .param("fullName", TEST_FULL_NAME)
                        .param("email", TEST_USER_EMAIL)
                        .param("imageUrl", TEST_IMAGE_URL)
                        .param("age", String.valueOf(TEST_USER_AGE))
                        .param("experience", String.valueOf(TEST_EXPERIENCE))
                        .param("currentCapital", String.valueOf(CURRENT_CAPITAL))
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("roleId", String.valueOf(ROLE_ID))
                        .with(csrf()).

                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).
                andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, userRepository.count());

        Optional<UserEntity> newCreatedUserOpt = userRepository.findByUsername(TEST_USERNAME);

        Assertions.assertTrue(newCreatedUserOpt.isPresent());

        UserEntity newUser = newCreatedUserOpt.get();

        Assertions.assertEquals(TEST_USERNAME, newUser.getUsername());
        Assertions.assertEquals(TEST_FULL_NAME, newUser.getFullName());
        Assertions.assertEquals(TEST_USER_EMAIL, newUser.getEmail());
        Assertions.assertEquals(TEST_USER_AGE, newUser.getAge());
        Assertions.assertEquals(TEST_EXPERIENCE, newUser.getExperience());
        Assertions.assertEquals(TEST_IMAGE_URL, newUser.getImageUrl());
        Assertions.assertEquals(CURRENT_CAPITAL.setScale(2), newUser.getCurrentCapital());
    }
}