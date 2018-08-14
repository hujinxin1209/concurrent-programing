package chapter03;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 			读			写
 * 
 * 读		非阻塞		阻塞
 * 
 * 写		阻塞			阻塞
 */
public class ReadWriteLockDemo {
	private static Lock lock = new ReentrantLock();
	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private static Lock readLock = readWriteLock.readLock();
	private static Lock writeLock = readWriteLock.writeLock();
	private int value;
	
	public Object handleRead(Lock lock) throws InterruptedException{
		try {
			lock.lock(); // 模拟读操作
			Thread.sleep(2000); // 读操作耗时越多，读写锁优势越明显
			return value;
		} finally {
			lock.unlock();
		}
	}
	
	public void handleWrite(Lock lock, int index) throws InterruptedException{
		try {
			lock.lock();
			Thread.sleep(1000);
			value = index;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final ReadWriteLockDemo demo = new ReadWriteLockDemo();
		Runnable readRunnable = new Runnable() {
			public void run() {
				try {
					demo.handleRead(readLock);
					//demo.handleRead(lock);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Runnable writeRunnable = new Runnable() {
			public void run() {
				try {
					demo.handleWrite(writeLock, new Random().nextInt());
					//demo.handleWrite(lock, new Random().nextInt());
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		for(int i = 0; i < 18; i++) {
			new Thread(readRunnable).start();
		}
		for(int i = 18; i < 20; i++) {
			new Thread(writeRunnable).start();
		}
	}
}
