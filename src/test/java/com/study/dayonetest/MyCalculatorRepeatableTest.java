package com.study.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MyCalculatorRepeatableTest {

    @RepeatedTest(5)
    @DisplayName("덧셈 5번 반복 테스트")
    public void repeatedAddTest() {
        // Arrange - 준비
        MyCalculator myCalculator = new MyCalculator();

        // Act - 행동
        myCalculator.add(10.0);

        // Assert - 검증
        Assertions.assertEquals(10.0, myCalculator.getResult());
    }

    @ParameterizedTest
    @MethodSource("parameterizedTestParameters")
    @DisplayName("덧셈 5번 파라미터 반복 테스트")
    public void parameterizedTest(Double addValue, Double expectValue) {
        // Arrange - 준비
        MyCalculator myCalculator = new MyCalculator(0.0);

        // Act - 행동
        myCalculator.add(addValue);

        // Assert - 검증
        Assertions.assertEquals(expectValue, myCalculator.getResult());
    }

    public static Stream<Arguments> parameterizedTestParameters() {
        return Stream.of(
                Arguments.of(10.0, 10.0),
                Arguments.of(8.0, 8.0),
                Arguments.of(4.0, 4.0),
                Arguments.of(16.0, 16.0),
                Arguments.of(13.0, 13.0)
        );
    }

    @ParameterizedTest
    @MethodSource("parameterizedComplicatedCalculateTestParameters")
    @DisplayName("사칙연산 파라미터 2번 반복 테스트")
    public void parameterizedComplicatedCalculateTest(Double addValue, Double minusValue,
                                                      Double multiplyValue, Double divideValue,
                                                      Double expectValue) {
        // given
        MyCalculator myCalculator = new MyCalculator(0.0);

        // when
        Double result = myCalculator
                .add(addValue)
                .minus(minusValue)
                .multiply(multiplyValue)
                .divide(divideValue)
                .getResult();

        // then
        Assertions.assertEquals(expectValue, result);
    }

    public static Stream<Arguments> parameterizedComplicatedCalculateTestParameters() {
        return Stream.of(
                Arguments.of(10.0, 4.0, 2.0, 3.0, 4.0),
                Arguments.of(4.0, 2.0, 4.0, 4.0, 2.0)
        );
    }
}
