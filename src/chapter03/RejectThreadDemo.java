package chapter03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 拒绝策略
// 自定义线程池
public class RejectThreadDemo {
	public static class MyTask implements Runnable {
		public void run() {
			System.out.println(System.currentTimeMillis() + ": Thread ID:" + Thread.currentThread().getId());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		MyTask task = new MyTask();
		ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(10), new RejectedExecutionHandler() {
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.out.println(r.toString() + " is discard");
					}
				});
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			es.submit(task);
			Thread.sleep(10);
		}
	}

}