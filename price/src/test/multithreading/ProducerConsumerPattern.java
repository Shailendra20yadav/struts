package test.multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProducerConsumerPattern {

   public static void main(String args[]){
 
    //Creating shared object
    BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<Integer>(5);

    //Creating Producer and Consumer Thread
    Thread prodThread = new Thread(new Producer(sharedQueue));
    Thread consThread = new Thread(new Consumer(sharedQueue));

    //Starting producer and Consumer thread
    prodThread.start();
    consThread.start();
   }

}

//Producer Class in java
class Producer implements Runnable {

   private final BlockingQueue<Integer> sharedQueue;

   public Producer(BlockingQueue<Integer> sharedQueue) {
       this.sharedQueue = sharedQueue;
   }

   @Override
   public void run() {
       for(int i=0; i<100; i++){
           try {
               System.out.println("Produced: " + i);
               sharedQueue.put(i);
           } catch (InterruptedException ex) {
               Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       try {
		sharedQueue.put(110);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }

}

//Consumer Class in Java
class Consumer implements Runnable{

   private final BlockingQueue<Integer> sharedQueue;

   public Consumer (BlockingQueue<Integer> sharedQueue) {
       this.sharedQueue = sharedQueue;
   }
 
   @Override
   public void run() {
       while(true){
           try {
        	   Integer data = sharedQueue.take();
               System.out.println("Consumed: "+ data);
               if (data.intValue() > 100)
            	   break;
           } catch (InterruptedException ex) {
               Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
 
 
}


