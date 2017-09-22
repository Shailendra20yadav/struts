package test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ErrorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runOutOfMemory();
		int size = Integer.MAX_VALUE;
		Set<Object> m = new HashSet<>();
			try {
				byte[] bytes = new byte[size];
			m.add(bytes);
			System.out.println("Object created ");
			System.out.println("Object added in set = "+ m.size());
			}catch(Error er){
				System.out.println("Error occured " + er.getMessage());
				er.printStackTrace();
				System.out.println("Object added in set after error = "+ m.size());
			}

	}
	private static final int MEGABYTE = (1024*1024);
	public static void runOutOfMemory() {
	    MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
	    for (int i=1; i <= 100; i++) {
	        try {
	           // byte[] bytes = new byte[MEGABYTE*500000];
	        	byte[] bytes = new byte[Integer.MAX_VALUE];
	        } catch (Exception e) {
	            e.printStackTrace();
	        } catch (OutOfMemoryError e) {
	            MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
	            long maxMemory = heapUsage.getMax() / MEGABYTE;
	            long usedMemory = heapUsage.getUsed() / MEGABYTE;
	            System.out.println(i+ " : Memory Use :" + usedMemory + "M/" + maxMemory + "M");
	        }
	    }
	}

}
