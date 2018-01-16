package test;

public class Factorial {

	public static void main(String[] args) {
		//int result =fact(5);
		//System.out.println("result= "+result);
		int array [] = {1, 2, 3,4,5};
		System.out.println(sumOfArray(array));

	}
	 static int fact(int x) {
		  if (x == 1) { 
			  System.out.println("x = 1");
		    return 1;  
		  } else {      
		    int returnValue =  x * fact(x-1);
		    System.out.println(" For x = " +x+" return value is ="+returnValue);
		    return returnValue;
		  }
		}
	 
	 public static int sumOfArray(int [] arr){
		 return sum(arr,0,0);
		 
	 }
	 
	 private static int sum(int[] array, int idx, int acc) {
		    if (idx == array.length)
		        return acc;
		    return sum(array, idx+1, acc+array[idx]);
		}

}
