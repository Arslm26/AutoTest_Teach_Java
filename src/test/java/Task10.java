import java.util.Arrays;

public class Task10 {

    public static String[] reverse(String[] arr) {
        String[] res = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[arr.length - 1 - i];

        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(Task10.reverse(new String[]{"1", "2", "3", "4", "5"})));
    }
}
