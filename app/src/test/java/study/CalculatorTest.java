package study;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class CalculatorTest extends TestCase {

    private Calculator mCalculator;

    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();
    }

    @Test
    public void testSum() throws Exception {
        //expected: 6, sum of 1 and 5
        assertEquals(6, mCalculator.sum(1, 5), 0);
    }
}