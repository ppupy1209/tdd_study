package com.study.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LombokTestDataTest {

    @Test
    public void testDataTest() {
        TestData testData = new TestData();

        testData.setName("kim");

        Assertions.assertEquals("kim",testData.getName());
    }
}
