package TestRun;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {

    private BasicJava basicJava;
    private Random random;

    @BeforeEach
    public void setUp() {
        basicJava = new BasicJava();
        random = new Random();
        System.out.println("========================Test method start");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // Запуск одного раза со случайным числом от 1 до 100
    @Test
    public void testIsEven() {
        int n = random.nextInt(100) + 1; // от 1 до 100 включительно
        boolean result = basicJava.isEven(n);
        assertEquals(n % 2 == 0, result);
    }

    // Запуск 20 раз со случайными числами от 0 до 99
    @RepeatedTest(20)
    public void testCheckAccess() {
        int age = random.nextInt(100); // от 0 до 99 включительно
        String result = basicJava.checkAccess(age);
        assertNotNull(result);
    }

    //Параметризованный тест с массивом (потоком) случайных чисел от 0 до 100
    @ParameterizedTest
    @MethodSource("generateRandomScores")
    public void testGetGrade(int score) {
        String result = basicJava.getGrade(score);
        assertNotNull(result);
    }
    //Вспомогательный метод для @ParameterizedTest
    static Stream<Integer> generateRandomScores() {
        Random rand = new Random();
        // Создаем поток из 3 случайных чисел от 0 до 100
        return Stream.generate(() -> rand.nextInt(101)).limit(3);
    }

    @Test
    public void testIsPossitive() {
        boolean result = BasicJava.isPositive(5);
        int n = random.nextInt();
        if (result == (n>=0)) {
            System.out.println("TEST PASSED");
        }
        else{
            System.out.println("TEST FAILED");
        }
    }

    @RepeatedTest(3)
    public void testblastOff() {
        int i = random.nextInt(5);
        String result = basicJava.blastOff(i);
        String ExpectResult;
        if (i == 0) {
            ExpectResult = "Поехали!";
        } else {
            ExpectResult = String.valueOf(i);
        }
        if (result.equals(ExpectResult)) {
            System.out.println(i + " : " + result + "TEST PASSED");
        } else {
            System.out.println(i + "TEST FAILED");
        }
        }

    @Test
    public void testSumToN() {
        Random rand = new Random();
        int n = rand.nextInt(50) + 1; // Случайное число от 1 до 50

        int result = BasicJava.sumToN(n);

        // Вычисляем правильный результат по формуле, чтобы с чем-то сравнить
        int expected = n * (n + 1) / 2;

        if (result == expected) {
            System.out.println("TEST PASSED (Число: " + n + ")");
        } else {
            System.out.println("TEST FAILED (Число: " + n + ")");
        }
    }
    @Test
    public void testHasBug() {
        String[] messages = {"Error", "Warning", "Bug", "Info"};
        boolean result = BasicJava.hasBug(messages);
        if (result) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @RepeatedTest(3)
    public void testGetEvenInRange() {
        Random rand = new Random();
        int start = rand.nextInt(5) * 2;
        int end = start + 10;
        String result = BasicJava.getEvenInRange(start, end);

        if (result.trim().startsWith(String.valueOf(start))) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    public void testFindMax() {
        Random rand = new Random();
        int[] arr = {rand.nextInt(100), rand.nextInt(100), 999, rand.nextInt(100)};
        int result = BasicJava.findMax(arr);

        if (result == 999) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @Test
    public void testReverse() {
        String[] arr = {"А", "Б", "В"};
        String[] result = BasicJava.reverse(arr);

        if (result[0].equals("В") && result[2].equals("А")) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
    @Test
    public void testRemoveSpecificName() {
        List<String> list = new ArrayList<>(Arrays.asList("Arslan", "Aslan", "Alan", "Ivan"));
        String nameToRemove = "Ivan";
        List<String> result = BasicJava.removeSpecificName(list, nameToRemove);

        if (result.size() == 2 && !result.contains("Ivan")) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
}

