public class Task7 {

    public static boolean hasBug(String[] messages){
        for (String message:messages){
            if (message.equalsIgnoreCase("Bug")){
                return true;
            }
        }
        return false;


    }

    public static void main(String[] args){
        String[] messages = {"Hello", "World", "Bug"};
        System.out.println(hasBug(messages));

    }
}
