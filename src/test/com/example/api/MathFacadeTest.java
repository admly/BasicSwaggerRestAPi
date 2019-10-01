package com.example.api;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MathFacadeTest {

    private final MathFacade mathFacade = new MathFacade();

    @Test
    public void divideTwoValues() {
        assertEquals(0.5, mathFacade.divideTwoValues(1, 2));
    }

    @Test
    public void addTwoValues() {
        assertEquals(3.0, mathFacade.addTwoValues(1, 2));
    }

}
