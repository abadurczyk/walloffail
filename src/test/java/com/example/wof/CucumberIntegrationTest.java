package com.example.wof;

import com.example.wof.comment.CommentDto;
import com.example.wof.fails.FailEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Suite
@SelectClasspathResource("features")
@CucumberContextConfiguration
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberIntegrationTest {

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    private FailEntity createdFail;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:tc:postgresql:latest:///staffaccess");
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @When("A new fail is created with the description {string}")
    public void foo(String description) throws Exception {

        MvcResult result = mockMvc.perform(put("/fails/" + description)).andExpect(status().isOk()).andReturn();
        createdFail = mapper.readValue(result.getResponse().getContentAsByteArray(), FailEntity.class);

    }

    @Then("{int} fail\\(s) exist")
    public void failSExist(int numberOfFails) throws Exception {


        MvcResult result = mockMvc.perform(get("/fails/all")).andReturn();


        List<FailEntity> failResponses = mapper.readValue(result.getResponse().getContentAsByteArray(),
                mapper.getTypeFactory().constructCollectionType(List.class, FailEntity.class));

        assertThat(failResponses).hasSize(numberOfFails);

    }

    @When("A comment is added with text {string} and rating of {int} stars")
    public void aCommentIsAddedWithTextAndRatingOfStars(String comment, int rating) throws Exception {

        assertThat(createdFail).isNotNull();
        assertThat(createdFail.getId()).isNotNull();
        CommentDto commentDto = CommentDto.builder()
                .comment(comment)
                .stars(rating)
                .failId(createdFail.getId())
                .build();
        mockMvc.perform(post("/comment/")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commentDto)))
                .andExpect(status().isOk()).andReturn();
    }
}
