package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SingleResult
 */
public class SingleResult {

    @JsonProperty("result")
    private Double result;

    public SingleResult(){}

    public SingleResult(Double result) {
        this.result = result;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }


}

