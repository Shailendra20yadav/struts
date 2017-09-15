package test;

import java.util.Hashtable;

public class Hello {
	//private final Hello(){} // constructor can not be final
	public static void main(String ...args){
		int x=4;
		System.out.println(++x*6);
		int a=2;
		int b=5;
		double c=  Math.sqrt(a*a + b*b);
		System.out.println(c);
		int num[] ={1,2,3,4,5,6,7,8,9,10};
		int result=0;
		for(int j=0;j<10;++j){
			result=result+num[j];
			
		}
		System.out.println(result/10);
		
		Hashtable table = new Hashtable<String,String>();
		
		table.put("m",100);
		table.put(1000, "test");
		
		int x1=5;
		{
			int y=4;
			System.out.println(x1+" "+y);
		}
		//System.out.println(x1+" "+y);
		
		
	}

}
