package ru.kpfu.itis.hyperbot.BMsubstring;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Random;

public class AlgorithmTester {
    public static void startTest(int size) {
        int tries = 7;
        int templateLength = 5;
        Random r = new Random();
        long allMillis[] = new long[tries];
        long allIterations[] = new long[tries];
        for(int j = 0; j < tries; j++) {
            StringBuilder sb = new StringBuilder();
            System.out.println("try " + j);
            for (int i = 0; i < templateLength; i++) {
                sb.append((char) r.nextInt(255));
            }
            String template = sb.toString();
            sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append((char) r.nextInt(255));
            }

            String text = sb.toString();
            Long[] iterations = {0l};
            long millis = System.currentTimeMillis();
            BMSubstringFinder.indexOf(text, template, iterations);
            allMillis[j] = System.currentTimeMillis() - millis;
            allIterations[j] = iterations[0];
        }
        long time = (long)(Arrays.stream(allMillis).average().getAsDouble());
        long iterations = (long)Arrays.stream(allIterations).average().getAsDouble();
        writeResult(size, time, iterations, 0);
        System.out.println("Test finised " +  size+ " " + " " + time + "ms " + iterations + "i");

        System.gc();
    }

    private static void writeResult(int size, long time, long iterations, int dispersion) {
        try {
            String filename = "test results.csv";
            File file = new File(filename);
            String data = String.valueOf(size) + "," + String.valueOf(time) + "," + String.valueOf(iterations) + "," + String.valueOf(dispersion) + "\n";
            Files.write(file.toPath(), data.getBytes("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
