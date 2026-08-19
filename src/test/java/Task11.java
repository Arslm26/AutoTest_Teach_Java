
import java.util.List;
import java.util.Arrays;

public class Task11 {

    public static double calcAverage(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (int num : list) {
            sum += num;
        }

        return (double) sum / list.size();
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 15, 20, 25);

        double average = Task11.calcAverage(numbers);
        System.out.println(average);
    }
}