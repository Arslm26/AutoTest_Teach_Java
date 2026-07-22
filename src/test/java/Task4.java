public class Task4 {
    public static String getGrade(int score){
        String grade;
        if (81 <= score && score <= 100)
            grade = "A";
        else if (61 <= score && score <= 80)
            grade = "B";
        else if (41 <= score && score <= 60)
            grade = "C";
        else if (21 <= score && score <= 40)
            grade = "D";
        else if (0 <= score && score <= 90)
            grade = "E";
        else grade = "Error";
        return grade;
    }

    public static void main(String[] args) {
        System.out.println(getGrade(100));
        System.out.println(getGrade(80));
        System.out.println(getGrade(61));
        System.out.println(getGrade(40));
        System.out.println(getGrade(18));
        System.out.println(getGrade(0));
        System.out.println(getGrade(-1));

    }
}
