package com.example.api;

import org.springframework.stereotype.Service;

@Service
public class MathFacade {

    /**
     * Divide two values
     * @param val1
     * @param val2
     * @return
     */
    double divideTwoValues(double val1, double val2){
        return val1 / val2;
    }

    /**
     * Adds two values
     * @param val1
     * @param val2
     * @return
     */
    double addTwoValues(double val1, double val2){
        return val1+val2;
    }
}
