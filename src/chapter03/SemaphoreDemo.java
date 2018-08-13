package chapter03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo implements Runnable{
	final Semaphore semp = new Semaphore(5);
	public void run() {
		try {
			semp.acquire();
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getId() + " done");
			semp.release();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		ExecutorService eService = Executors.newFixedThreadPool(20);
		final SemaphoreDemo sDemo = new SemaphoreDemo();
		for(int i = 0; i < 20; i++) {
			eService.submit(sDemo);
		}
	}
}
