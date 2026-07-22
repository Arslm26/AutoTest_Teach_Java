import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Task5 {
    public static String blastOff(int start){
        String result = "";
        for (int i = start; i > 0; i--) {
            System.out.println(i);

        };
        return result + "Поехали!";
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(blastOff(5));
        System.out.println(blastOff(3));
    }
}
