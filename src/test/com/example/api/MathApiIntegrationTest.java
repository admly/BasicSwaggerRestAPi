package com.example.api;

import com.example.model.SingleResult;
import com.example.model.Values;
import com.example.rest.ErrorMapResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MathApiIntegrationTest {

    private static final String VAL_2 = "val2";
    private static final String VAL_1 = "val1";
    private static final String DIV_ENDPOINT = "/api/v1/div";
    private static final String ADD_ENDPOINT = "/api/v1/add";
    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void divideTwoValues() {
        ResponseEntity<SingleResult> response = restTemplate.getForEntity(DIV_ENDPOINT + "?val1=4&val2=2", SingleResult.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(new Double(2), response.getBody().getResult());
    }

    @Test
    public void divideTwoValuesWithNullValues() {
        ResponseEntity<ErrorMapResponse> response = restTemplate.getForEntity(DIV_ENDPOINT + "?val1=&val2=", ErrorMapResponse.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getConstraintViolations().get(VAL_2), "may not be null");
    }

    @Test
    public void divideTwoValuesWithNotValidDivisor() {
        ResponseEntity<ErrorMapResponse> response = restTemplate.getForEntity(DIV_ENDPOINT + "?val1=4&val2=0", ErrorMapResponse.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getConstraintViolations().get(VAL_2), "You can't divide by zero");
    }

    @Test
    public void addTwoValues() {
        Values values = new Values();
        values.setVal1(2.0);
        values.setVal2(1.0);
        ResponseEntity<SingleResult> response = restTemplate.postForEntity(ADD_ENDPOINT, values, SingleResult.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getResult(), new Double (3.0));
    }

    @Test
    public void addTwoValuesWithNullValues() {
        Values values = new Values();
        ResponseEntity<ErrorMapResponse> response = restTemplate.postForEntity(ADD_ENDPOINT, values, ErrorMapResponse.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getConstraintViolations().get(VAL_2), "may not be null");
        assertEquals(response.getBody().getConstraintViolations().get(VAL_1), "may not be null");
    }
}
