import java.util.ArrayList;
import java.util.List;
 
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
 
/**
 * Есть счетчик, подсчитывающий количество вызовов.
 * 
 * Почему счетчик показывает разные значения и не считает до конца?
 * Как это можно исправить?
 * 
 * Попробуйте закомментировать обращение к yield().
 * Измениться ли значение?
 */
public class AtomicCounterTutorVer5 {
	
	    volatile int counter = 0;

	class TestThread implements Runnable {
		String threadName;
		private int local_counter;
		public TestThread(String threadName) {
			this.threadName = threadName;
		}
		
		@Override
		public void run() {
			local_counter = counter;
			for (int i=0;i<10000;i++) {
				local_counter++;
				Thread.yield();
			}
			counter =local_counter + counter;
			
		}
		public int getLocalCounter(){
			return this.local_counter;
		}
	}
	
	@Test
	public void testThread() {
		List<Thread> threads = new ArrayList<Thread>();
		for (int i=0;i<100;i++) {
			threads.add(new Thread(new TestThread("t"+i)));
		}
	    System.out.println("Starting threads");
		for (int i=0;i<100;i++) {
			threads.get(i).start();
		}
	    try {
			for (int i=0;i<100;i++) {
				threads.get(i).join();
				
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    System.out.println("Counter="+counter);
 
	}
 
}