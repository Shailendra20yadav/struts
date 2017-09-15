package test;
public class Deadlock1 {

  public static void main(final String[] args) throws Exception {

    final Object lockX = new Object();
    final Object lockY = new Object();

    final Thread threadA = new Thread(new Runnable() {
      @Override
      public void run() {
        
               synchronized (lockX) {
            	   System.out.println("Acquire lock-X");
        	 try {
     			Thread.sleep(1000);
     		} catch (InterruptedException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
          
          synchronized (lockY) {
        	  System.out.println("Acquire lock-Y");
            System.out.println("Both locks are acquired");
          }
          System.out.println("Release lock-Y");
        }
        System.out.println("Release lock-X");
      }
    }, "Thread-A");
   

    final Thread threadB = new Thread(new Runnable() {
      @Override
      public void run() {
        
        synchronized (lockY) {
        	System.out.println("Acquire lock-Y");
          synchronized (lockX) {
        	  System.out.println("Acquire lock-X");
            System.out.println("Both locks are acquired");
          }
          System.out.println("Release lock-X");
        }
        System.out.println("Release lock-Y");
      }
    }, "Thread-B");
    threadA.start();
    threadB.start();
    
    
    /* Wait for the threads to stop */
    threadA.join();
    threadB.join();
  }
}