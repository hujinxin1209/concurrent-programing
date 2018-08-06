package chapter03;

/*
 *ReentrantLock synchronized 
 * 两者的共同点：
1）协调多线程对共享对象、变量的访问
2）可重入，同一线程可以多次获得同一个锁
3）都保证了可见性和互斥性
两者的不同点：
1）ReentrantLock显示获得、释放锁，synchronized隐式获得释放锁
2）ReentrantLock可响应中断、可轮回，synchronized是不可以响应中断的，为处理锁的不可用性提供了更高的灵活性
3）ReentrantLock是API级别的，synchronized是JVM级别的
4）ReentrantLock可以实现公平锁
5）ReentrantLock通过Condition可以绑定多个条件
6）底层实现不一样， synchronized是同步阻塞，使用的是悲观并发策略，lock是同步非阻塞，采用的是乐观并发策略

虽然ReentrantLock可以提供比synchronized更高级的功能，但是仍不能替换synchronized
《java并发编程实战》:如果使用reentrantlock时，没有释放锁，很难追踪到最初发生错误的位置，因为没有记录应该释放锁的位置和时间
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterLock implements Runnable {
	public static ReentrantLock lock = new ReentrantLock();
	public static int i = 0;

	@Override
	public void run() {
		for (int j = 0; j < 100000; j++) {
			lock.lock();
			try {
				i++;
			} finally {
				lock.unlock();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		ReenterLock reenterLock = new ReenterLock();
		Thread t1 = new Thread(reenterLock);
		Thread t2 = new Thread(reenterLock);
		
		t1.start(); t2.start();
		t1.join(); t2.join();
		System.out.println(i);
	}

}
