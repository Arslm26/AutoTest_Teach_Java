public class Task3 {

    public static boolean isPositive(int n) {
        String ternaryStatus = (n>=0) ? "True" : "False";
        return ternaryStatus.equals("True");
    }

    public static void main(String[] args) {
        System.out.println(isPositive(10));
        System.out.println(isPositive(0));
        System.out.println(isPositive(-1));
    }
}