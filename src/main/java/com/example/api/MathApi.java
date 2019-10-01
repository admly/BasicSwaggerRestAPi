package com.example.api;

import com.example.api.control.Divisor;
import com.example.model.SingleResult;
import com.example.model.Values;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@Api
public class MathApi {

    private static final Logger log = LoggerFactory.getLogger(MathApi.class);

    private final MathFacade mathFacade;

    @Autowired
    public MathApi(MathFacade mathFacade) {
        this.mathFacade = mathFacade;
    }

    @ApiOperation(value = "Add given values", nickname = "addTwoValues", response = SingleResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SingleResult.class),
            @ApiResponse(code = 400, message = "Bad request")})
    @PostMapping(value = "/api/v1/add",
            produces = {"application/json"},
            consumes = {"application/json"})
    ResponseEntity<SingleResult> addTwoValues(@ApiParam(value = "Values object", required = true) @Valid @RequestBody Values body) {
        log.debug("invoked /api/v1/add");
        return new ResponseEntity<>(new SingleResult(mathFacade.addTwoValues(body.getVal1(), body.getVal2())), HttpStatus.OK);
    }


    @ApiOperation(value = "Get divided number (val1/val2)", nickname = "divideValues",
            notes = "Divide two given values. ",
            response = SingleResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Values divided", response = SingleResult.class),
            @ApiResponse(code = 400, message = "Bad request")})
    @GetMapping(value = "/api/v1/div",
            produces = {"application/json"})
    ResponseEntity<SingleResult> divideValues(@NotNull @ApiParam(value = "Dividend",
            required = true) @RequestParam(value = "val1") Double val1,
                                              @NotNull @ApiParam(value = "Divisor", required = true)
                                              @Divisor @RequestParam(value = "val2") Double val2) {
        log.debug("invoked /api/v1/div");
        return new ResponseEntity<>(new SingleResult(mathFacade.divideTwoValues(val1, val2)), HttpStatus.OK);
    }
}
