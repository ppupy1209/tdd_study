package com.study.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyCalculatorTest {

    @Test
    @DisplayName("MyCalculator 덧셈 테스트")
    void addTest() {

        // Arrange - 준비
        MyCalculator myCalculator = new MyCalculator();

        // Act - 행동
        myCalculator.add(10.0);

        // Assert - 검증
        Assertions.assertEquals(10.0,myCalculator.getResult());
    }

    @Test
    @DisplayName("MyCalculator 뺄셈 테스트")
    void minusTest() {
        // Given
        MyCalculator myCalculator = new MyCalculator(10.0);

        // When
        myCalculator.minus(5.0);

        // Then
        Assertions.assertEquals(5.0,myCalculator.getResult());
    }

    @Test
    @DisplayName("MyCalculator 곱셈 테스트")
    void multiplyTest() {
        MyCalculator myCalculator = new MyCalculator(2.0);

        myCalculator.multiply(2.0);

        Assertions.assertEquals(4.0,myCalculator.getResult());
    }

    @Test
    @DisplayName("MyCalculator 나눗셈 테스트")
    void divideTest() {
        MyCalculator myCalculator = new MyCalculator(10.0);

        myCalculator.divide(2.0);

        Assertions.assertEquals(5.0,myCalculator.getResult());
    }

    @Test
    @DisplayName("MyCalculator 사칙연산 테스트")
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
    @DisplayName("MyCalculator 0으로 나누었을 때에는 ZeroDivisionException 을 발생시켜야 함")
    void divideZeroTest() {

        // given
        MyCalculator myCalculator = new MyCalculator(10.0);

        // when & then
        Assertions.assertThrows(MyCalculator.ZeroDivisionException.class, () -> {
            myCalculator.divide(0.0);
        });
    }
}