package com.study.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyCalculatorTest {

    @Test
    void addTest() {

        // Arrange - 준비
        MyCalculator myCalculator = new MyCalculator();

        // Act - 행동
        myCalculator.add(10.0);

        // Assert - 검증
        Assertions.assertEquals(10.0,myCalculator.getResult());
    }

    @Test
    void minusTest() {
        // Given
        MyCalculator myCalculator = new MyCalculator(10.0);

        // When
        myCalculator.minus(5.0);

        // Then
        Assertions.assertEquals(5.0,myCalculator.getResult());
    }

    @Test
    void multiplyTest() {
        MyCalculator myCalculator = new MyCalculator(2.0);

        myCalculator.multiply(2.0);

        Assertions.assertEquals(4.0,myCalculator.getResult());
    }

    @Test
    void divideTest() {
        MyCalculator myCalculator = new MyCalculator(10.0);

        myCalculator.divide(2.0);

        Assertions.assertEquals(5.0,myCalculator.getResult());
    }

    @Test
    void complicatedCalculateTest() {
        // given
        MyCalculator myCalculator = new MyCalculator(0.0);

        // when
        Double result = myCalculator
                .add(10.0)
                .minus(4.0)
                .multiply(2.0)
                .divide(3.0)
                .getResult();

        // then
        Assertions.assertEquals(4.0,result);
    }

    @Test
    void divideZeroTest() {

        // given
        MyCalculator myCalculator = new MyCalculator(10.0);

        // when & then
        Assertions.assertThrows(MyCalculator.ZeroDivisionException.class, () -> {
            myCalculator.divide(0.0);
        });
    }
}