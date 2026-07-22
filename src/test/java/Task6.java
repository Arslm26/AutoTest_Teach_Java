public class Task6 {

    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum = sum + i;

        }
        return sum;
    }


    public static void main(String[] args) {
        System.out.println(sumToN(5));
        System.out.println(sumToN(3));
        System.out.println(sumToN(0));

    }
}
