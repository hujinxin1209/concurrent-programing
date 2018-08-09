package chapter03;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TimeLock implements Runnable{
	public static ReentrantLock lock = new ReentrantLock();
	public void run() {
		try {
			if(lock.tryLock(5, TimeUnit.SECONDS)) {
				Thread.sleep(6000);
			} else {
				System.out.println(Thread.currentThread().getId() + " : get lock failed");
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		TimeLock r1 = new TimeLock();
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r1);
		t1.start();
		t2.start();
	}
}
