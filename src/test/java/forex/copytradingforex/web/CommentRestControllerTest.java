package forex.copytradingforex.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import forex.copytradingforex.model.binding.NewCommentBindingModel;
import forex.copytradingforex.model.entity.CommentEntity;
import forex.copytradingforex.model.entity.PositionEntity;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.model.entity.enums.TradeEnum;
import forex.copytradingforex.repository.PositionRepository;
import forex.copytradingforex.repository.UserRepository;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WithMockUser("testUser")
@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {
    public static final String COMMENT_1 = "Comment number 1";
    public static final String COMMENT_2 = "Comment number 2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity testUser;

    @BeforeEach
    void setup() {

      userRepository.deleteAll();
        testUser = new UserEntity();
        testUser.setUsername("testUser")
                .setCurrentCapital(BigDecimal.valueOf(1000))
                .setPassword("12345")
                .setFullName("Test Testov")
                .setTotalWithdraw(BigDecimal.ZERO)
                .setEmail("test@test.ts")
                .setAge(33)
                .setExperience(3);

        testUser = userRepository.save(this.testUser);
    }

    @AfterEach
    void tearDown() {
        positionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getCommentsTest() throws Exception {
        var position = initComments(initPosition());

        mockMvc.perform(get("/api/" + position.getId() + "/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$.[0].textContent", is(COMMENT_1))).
                andExpect(jsonPath("$.[1].textContent", is(COMMENT_2)));
    }

    @Test
    void testCreateComment() throws Exception {
        NewCommentBindingModel testComment = new NewCommentBindingModel()
                .setTextContent(COMMENT_1);

        PositionEntity emptyPosition = initPosition();

        mockMvc.perform(
                        post("/api/" + emptyPosition.getId() + "/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testComment))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string("Location", MatchesPattern.matchesPattern("/api/" + emptyPosition.getId() + "/comments/\\d")))
                .andExpect(jsonPath("$.textContent").value(is(COMMENT_1)));


    }

    private PositionEntity initPosition() {
        PositionEntity positionTest = new PositionEntity();

        positionTest.setTrade(TradeEnum.BUY);
//                .setOpenTime(LocalDateTime.now())
//                .setCloseTime(LocalDateTime.now())
//                .setOpenPrice(BigDecimal.ONE)
//                .setClosePrice(BigDecimal.TEN)
//               .setFinancialResult(BigDecimal.TEN);
//                .setYield(BigDecimal.ONE);

        return positionRepository.save(positionTest);
    }

    private PositionEntity initComments(PositionEntity position) {

        CommentEntity comment1 = new CommentEntity();
        comment1.setCreated(LocalDateTime.now())
                .setAuthor(testUser)
                .setTextContent(COMMENT_1)
                .setApproved(true)
                .setPosition(position);

        CommentEntity comment2 = new CommentEntity();
        comment2.setCreated(LocalDateTime.now())
                .setAuthor(testUser)
                .setTextContent(COMMENT_2)
                .setApproved(true)
                .setPosition(position);


        position.setComments(List.of(comment1, comment2));

        return positionRepository.save(position);
    }


}