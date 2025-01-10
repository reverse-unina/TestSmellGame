package com.dariotintore.tesi;

import org.junit.*;
import static org.junit.Assert.*;



//Arrange-Act-Assert pattern

public class CalculatorTest {
    
private Calculator objCalcUnderTest;

@Before
public void setUp() {
//Arrange
objCalcUnderTest = new Calculator();
}

    @Test
    public void testAdd() {
        int a = 15; int b = 20;
        int expectedResult = 35;
        //Act
        long result = objCalcUnderTest.add(a, b);
        //Assert
        assertEquals(expectedResult, result);
        assertEquals(result, result);
    }

    @Test
    public void testSubtract() {
        int a = 25; int b = 20;
        int expectedResult = 5;
        long result = objCalcUnderTest.subtract(a, b);
        assertEquals(expectedResult, result);
    }

@Test
public void testMultiply() {
int a = 10; int b = 25;
long expectedResult = 250;
long result = objCalcUnderTest.multiply(a, b);
assertEquals(expectedResult, result);
}

@Test
public void testDivide() {
int a = 56; int b = 10; 
double expectedResult = 5.6; 
double result = objCalcUnderTest.divide(a, b);
assertEquals(expectedResult, result,0.00005); 
}



/* 
@Test(expected = IllegalArgumentException.class)
public void testDivideByZero() { 
int a = 15; int b = 0;
objCalcUnderTest.divide(a, b);
} 
*/
}
