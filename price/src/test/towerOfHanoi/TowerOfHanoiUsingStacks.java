package test.towerOfHanoi;

/*

 * Java Program to Solve Tower of Hanoi Problem using Stacks

The steps to follow are

Step 1 − Move n-1 disks from source to aux
Step 2 − Move nth disk from source to dest
Step 3 − Move n-1 disks from aux to dest

A recursive algorithm for Tower of Hanoi can be driven as follows −

START
Procedure Hanoi(disk, source, dest, aux)

   IF disk == 1, THEN
      move disk from source to dest             
   ELSE
      Hanoi(disk - 1, source, aux, dest)     // Step 1
      move disk from source to dest          // Step 2
      Hanoi(disk - 1, aux, dest, source)     // Step 3
   END IF
   
END Procedure
STOP

 */

 

 import java.util.*;

 

 /* Class TowerOfHanoiUsingStacks */

 public class TowerOfHanoiUsingStacks

 {

     public static int N;
     public static int moves=0;

     /* Creating Stack array  */

     public static Stack<Integer>[] tower = new Stack[4]; 

 

     public static void main(String[] args)

     {

         Scanner scan = new Scanner(System.in);

         tower[1] = new Stack<Integer>();

         tower[2] = new Stack<Integer>();

         tower[3] = new Stack<Integer>();

         /* Accepting number of disks */         

         System.out.println("Enter number of disks");

         int num = scan.nextInt();

         N = num;

         toh(num);

     }

     /* Function to push disks into stack */

     public static void toh(int n)

     {

         for (int d = n; d > 0; d--)

             tower[1].push(d);

         display();
         displayTowers();

         move(n, 1, 2, 3);         

     }

     /* Recursive Function to move disks */

     public static void move(int n, int a, int b, int c)

     {

         if (n > 0)

         {

             move(n-1, a, c, b);     

             int d = tower[a].pop();                                             

             tower[c].push(d);
             moves++;
            display();  
             displayTowers();

             move(n-1, b, a, c);     

         }         

     }
     private static void displayTowers(){
    	 
    	 System.out.println("tower 1= "+ tower[1]);
    	 System.out.println("tower 2= "+ tower[2]);
    	 System.out.println("tower 3= "+ tower[3]);
    	 System.out.println("--------------------------------Moves = "+moves+"-----------------------------------------------------");
     }
     

     /*  Function to display */

     public static void display()

     {

         System.out.println("  A  |  B  |  C");

         System.out.println("---------------");

         for(int i = N - 1; i >= 0; i--)

         {

             String d1 = " ", d2 = " ", d3 = " ";

             try

             {

                 d1 = String.valueOf(tower[1].get(i));

             }

             catch (Exception e){

             }    

             try

             {

                 d2 = String.valueOf(tower[2].get(i));

             }

             catch(Exception e){

             }

             try

             {

                 d3 = String.valueOf(tower[3].get(i));

             }

             catch (Exception e){

             }

             System.out.println("  "+d1+"  |  "+d2+"  |  "+d3);

         }

         System.out.println("\n");         

     }

 }
