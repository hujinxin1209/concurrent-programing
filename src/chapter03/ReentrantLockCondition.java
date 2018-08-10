package chapter03;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * wait() notify() 与 synchronized 关联
 * condition 与 重入锁关联 ReentrantLock :
 * 1) void await() throws InterruptedException
 * 2) void awaitUniterruptedException
 * 3) long awaitNanos(long nanosTimeout) throws InterruptedException
 * 4) boolean await(long time, TimeUnit unit) throws InterruptedException
 * 5) boolean awaitUntil(Date deadline) throws InterruptedException
 * 6) void signal()
 * 7) void signalAll()
 * i:await() 是当前线程等待，同事释放锁，当其他线程试用signal() 或者sigalAll()方法时，线程会重新获得锁，
 * 并继续执行。或者当线程被中断，也能挑出等待。这和Object.await()类似
 * ii:awaitUnInterruptibly() 方法与await()方法基本相同，区别，不会在等待过程中响应中断
 * iii: signal() 方法用于唤醒一个等待中的线程，相对的singalAll()唤醒所有等待中的线程
 * 
 */
public class ReentrantLockCondition implements Runnable {
	public static ReentrantLock lock = new ReentrantLock();
	public static Condition condition = lock.newCondition();

	public void run() {
		try {
			lock.lock();
			condition.await();
			System.out.println("Thread is going on");
		} catch (InterruptedException e) {

		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ReentrantLockCondition r1 = new ReentrantLockCondition();
		Thread t1 = new Thread(r1);
		t1.start();
		Thread.sleep(2000);
		// 通知t1继续执行
		lock.lock();
		condition.signal();
		lock.unlock();
	}
}
