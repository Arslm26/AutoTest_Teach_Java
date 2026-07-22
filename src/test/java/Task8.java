public class Task8 {

    public static String getEvenInRange(int start, int end) {
        String result = "";
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (result.isEmpty()) {
                    result = result + i + " ";
                } else {
                    result = result + i + " ";


                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Task8.getEvenInRange(2, 5));
        System.out.println(Task8.getEvenInRange(1, 10));
        System.out.println(Task8.getEvenInRange(1, 1));


    }
        }
