package test;

public class StaticTest {
	static int i=5;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StaticTest test=null;
		
		System.out.println("i value is "+test.i);
		StaticTest test1=new StaticTest();
		test1.i=6;
		System.out.println("i value is "+test1.i);
		System.out.println("i value is "+test.i);

	}

}
