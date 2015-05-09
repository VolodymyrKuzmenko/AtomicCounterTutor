import java.util.ArrayList;
import java.util.List;
 
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
public class AtomicCounterTutorVer3 {
	int  counter=0;
	Lock lock = new ReentrantLock();
	
	class TestThread implements Runnable {
		String threadName;
		
		public TestThread(String threadName) {
			this.threadName = threadName;
		}
		
		@Override
		public void run() {
			for (int i=0;i<10000;i++) {
				lock.lock();
				counter++;
				Thread.yield();
				lock.unlock();
			}
			
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