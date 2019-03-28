package ru.kpfu.itis.hyperbot.BMsubstring;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        for(int i = 0; i < 200; i++) {
            System.out.println("Running test " + i);
            AlgorithmTester.startTest((int)(10e6 + 10e4 * i));
        }
    }
}
