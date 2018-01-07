package test.towerOfHanoi;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class TohGame {

	 public static Stack<Integer> s1 = new Stack<Integer>();
	 public static Stack<Integer> s2 = new Stack<Integer>();
	 public static Stack<Integer> s3 = new Stack<Integer>();
	 static Map<String, Stack<Integer>> map = new HashMap<>();
	 public static int move,noOfDisk=0;
	//static Scanner s = new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Scanner s = new Scanner(System.in);
		map.put("a",s1);
		map.put("b",s2);
		map.put("c",s3);
		
		System.out.println("Enter no of Disk");
		
		noOfDisk = s.nextInt();
		//s.close();
		
		for(int i= noOfDisk; i>0 ;i--){
			s1.push(i);
		}
		
		displayTowers();
		takeInputForMove();

	}
	
	static void takeInputForMove(){
		if(s3.size() ==noOfDisk){
			System.out.println("Game Completed");
			System.exit(0);
		}
		System.out.println("Enter source tower from a to c");
		 Scanner s = new Scanner(System.in);
		String src = s.nextLine();
		src = src.toLowerCase();
		if(!(src.equals("a") || src.equals("b") || src.equals("c"))){
			System.err.println("Invalid input! for source tower");
			takeInputForMove();
		}
		System.out.println("Enter destination tower from a to c");
		String dest = s.nextLine();
		dest = dest.toLowerCase();
		
		if(!(dest.equals("a") || dest.equals("b") || dest.equals("c"))){
			System.err.println("Invalid input! for desination tower");
			takeInputForMove();
		}
		if(src.equals(dest)){
			System.err.println("Invalid input! source and destination can not be same");
			takeInputForMove();
		}
		moveDisk(src,dest);
	}
	static void moveDisk(String src,String dest){
		Stack<Integer> srcStack =map.get(src);
		Stack<Integer> destStack =map.get(dest);
		if(srcStack.isEmpty()){
			System.err.println("Invalid move Source tower is empty!");
			takeInputForMove();
		}
		if(destStack.isEmpty() || (srcStack.peek() < destStack.peek())){
			destStack.push(srcStack.pop());
			System.out.println("Move successful no of moves = "+ ++move);
			displayTowers();
			takeInputForMove();
		}else{
			System.err.println("Invalid move can not move larger disk (" + srcStack.peek() +" ) on a samller disk (" +destStack.peek() +" )" );
			takeInputForMove();
		}
	}
	
	public static void displayTowers(){
		System.out.println("Tower A = "+ s1);
		System.out.println("Tower B = "+ s2);
		System.out.println("Tower C = "+ s3);
		System.out.println("-------------------------------------------------------------------------------------");
	}

}
