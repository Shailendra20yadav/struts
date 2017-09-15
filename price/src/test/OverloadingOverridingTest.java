package test;

public class OverloadingOverridingTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SuperClass superClass =  new SuperClass();
		superClass.A(null); // String parameter called
		
		SuperClass superClass1 =  new Subclass();
		//superClass1.D();

	}

}
class SuperClass{
	
	public void A(Object a){
		System.out.println("object parameter called");
	}
	
	public void A (String s){
		System.out.println("String parameter called");
	}
	
	protected  void  C(){
		System.out.println("Superclass C called ");
	}
	private void D(){
		System.out.println("Superclass D called ");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SuperClass superClass =  new SuperClass();
		superClass.A(null); // String parameter called
		
		SuperClass superClass1 =  new Subclass();
		superClass1.D();

	}
}
class Subclass extends SuperClass{
	
	public void A(Object A){ //can not reduce the visibility of method 
		System.out.println("Subclass A called ");
		
	}
	
	public void C(){ // can increase the visibility of method 
		System.out.println("Subclass C called ");
	} 
	
	public void D(){
		System.out.println("Subclass D called ");
	}
	
}
