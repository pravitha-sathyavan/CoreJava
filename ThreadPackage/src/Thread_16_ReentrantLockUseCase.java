package ThreadPackage.src;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread_16_ReentrantLockUseCase {

    private static double stockPrice = 100.0; // Initial stock price
    private static final Lock lock = new ReentrantLock();

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
                lock.lock(); // Acquire lock to update stock price
                try {
                    // Simulate network latency with reduced sleep
                    Thread.sleep(300);
                    // Update stock price randomly
                    stockPrice += (random.nextDouble() - 0.5) * 10; // Random change between -5 and +5
                    System.out.println("[Network Thread] Stock price updated: " + String.format("%.2f", stockPrice));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock(); // Release lock
                }
            }
        }
    }

    // Simulates UI reading the stock price and displaying it to the user
    static class UIUpdaterTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                lock.lock(); // Acquire lock to read stock price
                try {
                    // Simulate UI refresh rate
                    Thread.sleep(500);
                    // Read and display the current stock price
                    System.out.println("[UI Thread] Current stock price: " + String.format("%.2f", stockPrice));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock(); // Release lock
                }
            }
        }
    }
}

