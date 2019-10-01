package com.example.api;

import com.example.model.Values;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests with WebMvcTest approach.
 */
@RunWith(SpringRunner.class)
@WebMvcTest({MathApi.class})
public class MathApiMockMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MathFacade mathFacade;

    private Values values;

    private String testRequest;

    @Before
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        testRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/addValuesTest.json")),
                StandardCharsets.UTF_8);
        values = objectMapper.readValue(testRequest, Values.class);
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        given(mathFacade.addTwoValues(3.0, 1.0)).willReturn(4.0);

        this.mockMvc.perform(post("/api/v1/add")
                .content(testRequest)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":4.0}"));
    }

    @Test
    public void divideTwoValues() throws Exception {
        given(mathFacade.divideTwoValues(3.0, 1.0)).willReturn(3.0);

        this.mockMvc.perform(get("/api/v1/div").param("val1", values.getVal1().toString()).param("val2",
                values.getVal2().toString())
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":3.0}"));
    }

    @Test
    public void divideTwoValuesOneIsZero() throws Exception {
        given(mathFacade.divideTwoValues(3.0, 1.0)).willReturn(3.0);

        this.mockMvc.perform(get("/api/v1/div").param("val1", values.getVal1().toString()).param("val2",
                "0")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"constraintViolations\":{\"val2\":\"You can't divide by zero\"}}"));
    }
}
