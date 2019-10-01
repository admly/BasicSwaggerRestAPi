package com.example.api;

import com.example.model.Values;
import com.example.rest.ApiOriginFilter;
import com.example.rest.RestExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
 * Class for MathApiController unit tests using mockMvc standalone approach
 */
@RunWith(MockitoJUnitRunner.class)
public class MathApiTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MathApi mathApi;

    @Mock
    private MathFacade mathFacade;

    private Values values;

    private String testRequest;

    @Before
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        testRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/addValuesTest.json")),
                StandardCharsets.UTF_8);
        values = objectMapper.readValue(testRequest, Values.class);

        mockMvc = MockMvcBuilders.standaloneSetup(mathApi)
                .setControllerAdvice(new RestExceptionHandler())
                .addFilters(new ApiOriginFilter())
                .build();

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

}
