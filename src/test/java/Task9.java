public class Task9{

     public static int findMax(int[] arr){
        int max = arr[0];
        for(int i=0;i<arr.length;i++){
            if(arr[i]>max){
                max = arr[i];

            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(findMax(new int[]{1,2,3,4,5,6,7,8,9,10}));
    }
}

