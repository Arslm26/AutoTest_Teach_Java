import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task12 {

    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
      List<String> result = new ArrayList<>();
      for (String str : list) {
          if (!nameToRemove.equals(str)) {
              result.add(str);
          }
      }

      return result;
        }


        public static void main(String[] args) throws UnsupportedEncodingException {
            try {
                System.setOut(new PrintStream(System.out, true, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);

            }

            List<String> str = Arrays.asList("Arslan", "Aslan", "Alan", "Ivan");
            System.out.println("Данный список: " + str);
            List<String> result = removeSpecificName(str, "Arslan");
            System.out.println("После удаления: " + result);


        }
}

