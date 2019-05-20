package com.github.martinfrank.yahtzee;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PrinterTest {

    @Test
    public void printTest() {
        InputStream original = System.in;
        System.setIn(new ByteArrayInputStream("roll\nkeep 1 2 3 4\nexit\n".getBytes()));
        YahtzeeApp.main(new String[]{});
        System.setIn(original);
    }
}
