package com.jcobproject.dicegame2.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class DiceRollerServiceTest {

    @ParameterizedTest
    @MethodSource
    public void testCalculateScore(int[] input, int expected) {
        GameDice1000Service gameDice1000Service = new GameDice1000Service();

        int result = gameDice1000Service.calculateScore(input);

        assertEquals(expected, result);
    }

    public static Stream<Arguments> testCalculateScore(){
        return Stream.of(
                Arguments.of(new int[]{1,1,1,1,1}, 1000),
                Arguments.of(new int[]{2,2,2,2,2}, 1000),
                Arguments.of(new int[]{3,3,3,3,3}, 1000),
                Arguments.of(new int[]{4,4,4,4,4}, 1000),
                Arguments.of(new int[]{5,5,5,5,5}, 1000),
                Arguments.of(new int[]{6,6,6,6,6}, 1000),
                Arguments.of(new int[]{5,6,6,6,6}, 125),
                Arguments.of(new int[]{5,5,5,5,6}, 100),
                Arguments.of(new int[]{6,4,4,4,4}, 80),
                Arguments.of(new int[]{6,1,1,1,1}, 200),
                Arguments.of(new int[]{5,4,4,4,4}, 85),
                Arguments.of(new int[]{1,4,4,4,4}, 90),
                Arguments.of(new int[]{2,2,6,6,6}, 60),
                Arguments.of(new int[]{2,2,1,1,1}, 100),
                Arguments.of(new int[]{5,5,1,1,1}, 110),
                Arguments.of(new int[]{5,5,6,6,6}, 70),
                Arguments.of(new int[]{1,2,2,4,4}, 10),
                Arguments.of(new int[]{5,2,2,4,4}, 5),
                Arguments.of(new int[]{1,2,3,4,5}, 150)
        );
    }
}