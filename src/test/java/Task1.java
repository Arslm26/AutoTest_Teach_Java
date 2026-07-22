public class Task1 {
    public static boolean isEven(int n) {
        if (n % 2 == 0)
            return true;
        else
            return false;
    }


    public static void main(String[] args) {
        System.out.println(isEven(5));
        System.out.println(isEven(6));

        boolean isEven = isEven(10);
        System.out.println(isEven);

    }

}

