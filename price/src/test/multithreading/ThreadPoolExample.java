package test.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolExample {

    public static void main(String args[]) {
       
    	long startTime = System.currentTimeMillis();
    	//testRunnableTask();
    	testCallableTask();
    	long endTime = System.currentTimeMillis();
    	System.out.println("Time taken = "+( (endTime-startTime)));
    }
  
    public static void testRunnableTask(){
    	ExecutorService service = Executors.newFixedThreadPool(3);
    	for (int i =0; i<100; i++){
    		Future<?> f =     service.submit(new Task(i));
    		//service.execute(new Task(i));
    		try {
    			System.out.println("Returned Value from future = " +f.get());
    		} catch (InterruptedException | ExecutionException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    	}
    	service.shutdown();
    }
    public static void testCallableTask(){
    	ExecutorService service = Executors.newFixedThreadPool(10);
    	for (int i =0; i<100; i++){
    		//Future<?> f =     service.submit(new CallableTask(i));
    		service.submit(new CallableTask(i));

    		/*try {
    			System.out.println("Returned Value from future = " +f.get());
    		} catch (InterruptedException | ExecutionException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}*/
    	}
    	
    	service.shutdown();
    }
}


final class Task implements Runnable{
    private int taskId;
  
    public Task(int id){
        this.taskId = id;
    }
  
    @Override
    public void run() {
        System.out.println("Task ID : " + this.taskId +" performed by " 
                           + Thread.currentThread().getName());
    }
  
}

final class CallableTask implements Callable<Integer>{
    private int taskId;
  
    public CallableTask(int id){
        this.taskId = id;
    }
  
    @Override
    public Integer call() {
        System.out.println("Task ID : " + this.taskId +" performed by " 
                           + Thread.currentThread().getName());
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new Integer(this.taskId);
    }
  
}