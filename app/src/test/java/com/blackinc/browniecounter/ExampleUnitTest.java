package com.blackinc.browniecounter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for BrownieCounter logic.
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void counter_increment_and_decrement_logic() {
        int count = 0;
        count++;
        assertEquals(1, count);
        count++;
        assertEquals(2, count);
        count--;
        assertEquals(1, count);
        count = 0; // reset
        assertEquals(0, count);
    }
}