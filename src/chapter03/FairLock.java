package chapter03;

import java.util.concurrent.locks.ReentrantLock;

// synchronized 实现非公平锁，等待队列中随机挑选获得锁，产生饥饿现象
// ReentrantLock 可实现公平锁，按时间先后顺序，保证先到先得，不产生饥饿现象 
// 但是系统要维护一个有序队列，实现成本较高，性能相对低下，因此默认情况下，锁是非公平的。

public class FairLock implements Runnable {
	public static ReentrantLock fairLock = new ReentrantLock(true);

	public void run() {
		while (true) {
			try {
				fairLock.lock();
				System.out.println(Thread.currentThread().getName() + " get lock");
			} finally {
				if (fairLock.isHeldByCurrentThread()) {
					fairLock.unlock();
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		FairLock r1 = new FairLock();
		Thread t1 = new Thread(r1, "thread_1");
		Thread t2 = new Thread(r1, "thread_2");
		t1.start();
		t2.start();
	}
}
