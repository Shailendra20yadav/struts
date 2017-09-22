package test;

public class SubArraySum {
	public static void StoreMap(int array[], int sumReq){
        for(int i=0; i<array.length; i++) {
            String subStr=array[i]+" ,";
            int sum=array[i];
            for(int j=i+1; j<array.length; j++) {
                sum+=array[j];
                subStr+=array[j]+", ";
                if (sum==sumReq)
                    System.out.println(subStr);
            }
        }
    }
    
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5};
        StoreMap(array, 5);
    }

}
