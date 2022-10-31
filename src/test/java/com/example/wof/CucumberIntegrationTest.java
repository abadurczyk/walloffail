package com.example.wof;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Suite
@SelectClasspathResource("features")
@CucumberContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @When("A new fail is created with the description {string}")
    public void foo(String description) throws Exception {

        mockMvc.perform(put("/fails/" + description)).andExpect(status().isOk());

    }

    @Then("{int} fail\\(s) exist")
    public void failSExist(int numberOfFails) throws Exception {


        MvcResult mvcResult = mockMvc.perform(get("/fails/all")).andReturn();

        ObjectMapper mapper = new ObjectMapper();

        List<FailEntity> failResponses = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                mapper.getTypeFactory().constructCollectionType(List.class, FailEntity.class));

        assertThat(failResponses).hasSize(numberOfFails);

    }
}
