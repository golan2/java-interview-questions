package interview.multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class CheckReentrantLock {
	private final static ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new Locker(1));
		Thread thread2 = new Thread(new Locker(2));
		thread1.start();
		thread2.start();
		Thread.sleep(3000);
		System.out.println("Main - Interrupt1");
		thread1.interrupt();
		System.out.println("Main - Interrupt2");
		thread2.interrupt();
		System.out.println("Main - Done");
	}
	
	private static class Locker implements Runnable {
		
		private final int num;

		Locker(int i) {
			this.num = i;
		}

		public void run() {
			System.out.println("Locker["+num+"] - run BEGIN");
			try {
				lock.lock();
				System.out.println("Locker["+num+"] - Gained lock");
			} catch (Exception e1) {
				System.out.println("Locker["+num+"] - InterruptedException - lockInterruptibly");
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("Locker["+num+"] - InterruptedException - sleep");
			}
			lock.unlock();
			System.out.println("Locker["+num+"] - Released lock");
		}
		
	}

}
