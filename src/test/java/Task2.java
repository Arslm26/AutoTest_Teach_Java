public class Task2{
    public static String checkAccess(int age){
        if(age>18) {
            return "Allowed";
        } else  {
            return "Denied";
        }
        }


      public static void main (String[] args) {
        System.out.println(checkAccess(7));
        System.out.println(checkAccess(8));
        System.out.println(checkAccess(18));
        System.out.println(checkAccess(19));
      }

    }






