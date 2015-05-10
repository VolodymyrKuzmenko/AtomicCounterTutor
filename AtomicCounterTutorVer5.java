import java.util.ArrayList;
import java.util.List;
 
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
 
/**
 * ���� �������, �������������� ���������� �������.
 * 
 * ������ ������� ���������� ������ �������� � �� ������� �� �����?
 * ��� ��� ����� ���������?
 * 
 * ���������� ���������������� ��������� � yield().
 * ���������� �� ��������?
 */
public class AtomicCounterTutorVer5 {
	
	volatile long counter = 0;

	class TestThread implements Runnable {
		String threadName;
		
		public TestThread(String threadName) {
			this.threadName = threadName;
		}
		
		@Override
		public void run() {
			long local_count = counter;
			for (int i=0;i<10000;i++) {
				local_count++;
				Thread.yield();
			}
			counter =counter+local_count;
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