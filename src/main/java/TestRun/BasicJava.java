package TestRun;


import java.util.ArrayList;
import java.util.List;

public class BasicJava {

        public boolean isEven(int n) {
            return n % 2 == 0;
        }

        public String checkAccess(int age) {
            if (age >= 18) {
                return "Доступ разрешен";
            } else {
                return "Доступ запрещен";
            }
        }

        public String getGrade(int score) {
            if (score >= 90) return "A";
            if (score >= 80) return "B";
            if (score >= 70) return "C";
            if (score >= 60) return "D";
            return "F";
        }

        public static boolean isPositive(int n) {
            String ternaryStatus = (n >= 0) ? "True" : "False";
            return ternaryStatus.equals("True");
        }

        public static String blastOff(int start) {
            String result = "";
            for (int i = start; i > 0; i--) {
                System.out.println(i);

            }
            ;
            return result + "Поехали!";
        }

        public static int sumToN(int n) {
            int sum = 0;
            for (int i = 1; i <= n; i++) {
                sum = sum + i;

            }
            return sum;
        }

        public static boolean hasBug(String[] messages) {
            for (String message : messages) {
                if (message.equalsIgnoreCase("Bug")) {
                    return true;
                }
            }
            return false;

        }
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

        public static int findMax(int[] arr){
            int max = arr[0];
            for(int i=0;i<arr.length;i++){
                if(arr[i]>max){
                    max = arr[i];

                }
            }
            return max;
        }

        public static String[] reverse(String[] arr) {
            String[] res = new String[arr.length];
            for (int i = 0; i < arr.length; i++) {
                res[i] = arr[arr.length - 1 - i];

            }
            return res;
        }
    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        List<String> result = new ArrayList<>();
        for (String str : list) {
            if (!nameToRemove.equals(str)) {
                result.add(str);
            }
        }

        return result;
    }


}