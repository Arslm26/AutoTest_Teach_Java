package TestRun;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Tag("task2")
public class AssertionTest {

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


    @RepeatedTest(10)
    public void testblastOff() {
        int i = random.nextInt(5);
        String result = BasicJava.blastOff(i);

        String expected;
        if (i == 0) {
            expected = "Поехали!";
        } else if (i == 1) {
            expected = "1 Поехали!";
        } else if (i == 2) {
            expected = "2 1 Поехали!";
        } else if (i == 3) {
            expected = "3 2 1 Поехали!";
        } else {
            expected = "4 3 2 1 Поехали!";
        }

        assertEquals(expected, result,
                "Ошибка в blastOff для числа " + i + ": ожидалось '" + expected + "', но возвращаем '" + result + "'");

    }


    @RepeatedTest(10)
    public void testSumToN() {
        Random rand = new Random();
        int n = rand.nextInt(50) + 1;
        int result = BasicJava.sumToN(n);
        int expected = n * (n + 1) / 2;

        assertEquals(expected, result,
                "Ошибка в sumToN для числа " + n + ": ожидалась сумма " + expected + ", но получена " + result);
    }

    @RepeatedTest(10)
    public void testHasBug() {
        String[] messages = {"Error", "Warning", "Bug", "Info"};
        boolean result = BasicJava.hasBug(messages);

        assertTrue(result,
                "Ошибка в hasBug: ожидалось true (слово 'Bug' есть), но метод вернул false");
    }

    @RepeatedTest(10)
    public void testGetEvenInRange() {
        Random rand = new Random();
        int start = rand.nextInt(5) * 2;
        int end = start + 10;
        String result = BasicJava.getEvenInRange(start, end);

        assertTrue(result.startsWith(String.valueOf(start)),
                "Ошибка в getEvenInRange: строка должна начинаться с числа " + start + ", но получена строка: '" + result + "'");

    }

    @RepeatedTest(10)
    public void testFindMax() {
        Random rand = new Random();
        int[] arr = {rand.nextInt(100), rand.nextInt(100), 999, rand.nextInt(100)};
        int result = BasicJava.findMax(arr);

        assertEquals(999, result,
                "Ошибка в findMax: ожидалось 999, но получено " + result);
    }
    @RepeatedTest(10)
    public void testReverse() {
        String[] arr = {"А", "Б", "В"};
        String[] result = BasicJava.reverse(arr);

        assertEquals("В", result[0],
                "Ошибка в Reverse: 1 элемент массива ожидался 'В', но получен '" + result[0] + "'");

        assertEquals("А", result[2],
                "Ошибка в Reverse: 3 элемент массива ожидался 'А', но получен '" + result[2] + "'");
    }

    @RepeatedTest(10)
    public void testRemoveSpecificName() {
        List<String> inputList = new ArrayList<>(Arrays.asList("Arslan", "Aslan", "Alan", "Ivan"));
        String nameToRemove = "Ivan";

        List<String> expectedList = Arrays.asList("Arslan", "Aslan", "Alan");
        List<String> actualList = BasicJava.removeSpecificName(inputList, nameToRemove);

        assertEquals(expectedList, actualList,
                "Ошибка в RemoveSpecificName '" + nameToRemove + "'. Ожидался список: "
                        + expectedList + ", но был получен: " + actualList);
    }

    @RepeatedTest(10)
    public void testIsEven() {
        int n = random.nextInt(100) + 1;
        boolean result = BasicJava.isEven(n);
        boolean expected = (n % 2 == 0);

        assertEquals(expected, result,
                "Ошибка в isEven " + n + ". Ожидался результат: "
                        + expected + ", но был получен: " + result);

    }

    @RepeatedTest(10)
     public void testCheckAccess() {
        int age = random.nextInt(100);
        String result = BasicJava.checkAccess(age);
        String expected = (age >= 18) ? "Доступ разрешен" : "Доступ запрещен";

        assertEquals(expected, result,
                "Ошибка в CheckAccess " + age + ". Ожидалось: '"
                        + expected + "', но было получено: '" + result + "'");
    }

    @RepeatedTest(10)
    public void testGetGrade() {
        int score = random.nextInt(101);
        String result = basicJava.getGrade(score);

        String expected;
        if (score >= 90) expected = "A";
        else if (score >= 80) expected = "B";
        else if (score >= 70) expected = "C";
        else if (score >= 60) expected = "D";
        else expected = "F";

        assertEquals(expected, result,
                "Ошибка в GetGrade " + score + ". Ожидалась оценка: '"
                        + expected + "', но была получена: '" + result + "'");
    }

    @RepeatedTest(10)
    public void testIsPossitive() {
        int n = random.nextInt(201) - 100;
        boolean result = BasicJava.isPositive(n);
        boolean expected = (n > 0);

        assertEquals(expected, result,
                "Ошибка в isPossitive " + n + ". Ожидалось: "
                        + expected + ", но метод вернул: " + result);
    }

    @RepeatedTest(10)
    public void testCalcAverageNormalNumbers() {
        List<Integer> list = Arrays.asList(10, 20, 30, 40);
        double expected = 25.0;
        double actual = BasicJava.calcAverage(list);

        assertEquals(expected, actual, 0.001,
                "Ошибка в CalcAverage, Ожидалось: " + expected + ", но получено: " + actual);
    }
}