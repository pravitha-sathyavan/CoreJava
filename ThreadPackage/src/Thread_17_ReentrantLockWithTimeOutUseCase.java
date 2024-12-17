package ThreadPackage.src;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread_17_ReentrantLockWithTimeOutUseCase {

    private static double stockPrice = 100.0; // Initial stock price
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        // Thread 1: Fetch stock prices from network (simulated)
        Thread stockPriceUpdaterThread = new Thread(new StockPriceUpdaterTask(), "StockPriceUpdater");

        // Thread 2: UI Thread to read and display stock price
        Thread uiThread = new Thread(new UIUpdaterTask(), "UIUpdater");

        // Start both threads
        stockPriceUpdaterThread.start();
        uiThread.start();
    }

    // Simulates updating stock price from a network call
    static class StockPriceUpdaterTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    // Simulate network latency outside the critical section
                    Thread.sleep(300);
                    double change = (ThreadLocalRandom.current().nextDouble() - 0.5) * 10;
                    // Attempt to acquire the lock within 100 ms
                    if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            // Update stock price randomly
                            stockPrice += change; // Random change between -5 and +5
                            System.out.println("[Network Thread] Stock price updated: " + String.format("%.2f", stockPrice));
                        } finally {
                            lock.unlock(); // Release lock
                        }
                    } else {
                        System.out.println("[Network Thread] Couldn't acquire lock, skipping update.");
                    }
                } catch (InterruptedException e) {
                    System.out.println("[Network Thread] Interrupted, exiting.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    // Simulates UI reading the stock price and displaying it to the user
    static class UIUpdaterTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    // Simulate UI refresh rate outside the critical section
                    Thread.sleep(500);

                    // Attempt to acquire the lock within 100 ms
                    if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            // Read and display the current stock price
                            System.out.println("[UI Thread] Current stock price: " + String.format("%.2f", stockPrice));
                        } finally {
                            lock.unlock(); // Release lock
                        }
                    } else {
                        System.out.println("[UI Thread] Couldn't acquire lock, skipping read.");
                    }
                } catch (InterruptedException e) {
                    System.out.println("[UI Thread] Interrupted, exiting.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
