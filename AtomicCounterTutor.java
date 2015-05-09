import java.util.ArrayList;
import java.util.List;
 
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
public class AtomicCounterTutor {
	int  counter=0;
 
	public synchronized void increment(){
		counter++;
	}
	class TestThread implements Runnable {
		String threadName;
		
		public TestThread(String threadName) {
			this.threadName = threadName;
		}
		
		@Override
		public void run() {
			for (int i=0;i<10000;i++) {
				increment();
				Thread.yield();
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