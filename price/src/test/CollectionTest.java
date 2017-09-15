package test;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class CollectionTest {
	public static void main(String args []){
		
		Set s =new TreeSet();
		s.add("1");
		s.add(2);
		s.add(3);
		//Ite
		
		Vector<String> v = new Vector<String>();
		v.add("1");
		v.add("2");
		Enumeration<String> e = Collections.enumeration(v); // fail-fast
		//Enumeration<String> e = v.elements(); // fail-safe
		while(e.hasMoreElements()){
			v.add("3");
			System.out.println(e.nextElement()+"");
		}
				
		}
	}
