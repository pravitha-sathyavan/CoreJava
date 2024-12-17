package ThreadPackage.src;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Thread_20_ReentrantReadWriteLockUseCase {

    private static double stockPrice = 100.0; // Initial stock price
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        // Thread 1: Fetch stock prices from network (simulated)
        Thread stockPriceUpdaterThread = new Thread(new StockPriceUpdaterTask());

        // Thread 2: UI Thread to read and display stock price
        Thread uiThread = new Thread(new UIUpdaterTask());

        // Start both threads
        stockPriceUpdaterThread.start();
        uiThread.start();
    }

    // Simulates updating stock price from a network call
    static class StockPriceUpdaterTask implements Runnable {
        Random random = new Random();

        @Override
        public void run() {
            while (true) {
                try {
                    // Simulate network latency
                    Thread.sleep(300);
                    double newPrice = stockPrice + (random.nextDouble() - 0.5) * 10;

                    // Lock for writing
                    lock.writeLock().lock();
                    try {
                        stockPrice = newPrice;
                        System.out.println("[Network Thread] Stock price updated: " + String.format("%.2f", stockPrice));
                    } finally {
                        lock.writeLock().unlock();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
                    // Simulate UI refresh rate
                    Thread.sleep(500);

                    // Lock for reading
                    lock.readLock().lock();
                    try {
                        System.out.println("[UI Thread] Current stock price: " + String.format("%.2f", stockPrice));
                    } finally {
                        lock.readLock().unlock();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


