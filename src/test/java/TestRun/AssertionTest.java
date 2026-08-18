package TestRun;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
        String result = basicJava.blastOff(i);
        String ExpectResult;
        if (i == 0) {
            ExpectResult = "Поехали!";
        } else {
            ExpectResult = String.valueOf(i);
        }
        assertEquals(ExpectResult, result,
                "Ошибка в blastOff: ожидалось '" + ExpectResult + "', но метод вернул '" + result + "'");
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

        assertEquals(result, messages,
                "Ошибка в hasBug: ожидалось true (слово 'Bug' есть), но метод вернул false");
    }

    @RepeatedTest(10)
    public void testGetEvenInRange() {
        Random rand = new Random();
        int start = rand.nextInt(5) * 2;
        int end = start + 10;
        String result = BasicJava.getEvenInRange(start, end);

        assertEquals(result, start,
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

        assertEquals("А", result[1],
                "Ошибка в Reverse: 2 элемент массива ожидался 'А', но получен '" + result[1] + "'");
    }
}