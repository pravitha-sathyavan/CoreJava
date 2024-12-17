package ThreadPackage.src;

import java.util.concurrent.locks.ReentrantReadWriteLock;
/*
    ReentrantReadWriteLock:
        ReadWriteLock allows multiple threads to read simultaneously but only one thread can write at a time.
        This avoids unnecessary contention when the UI thread reads the data.

    Separation of Read and Write:
        Network Thread acquires the write lock to update the stock price.
        UI Thread acquires the read lock to display the stock price.

    Correct Behavior of ReentrantReadWriteLock:
        If a write lock is acquired:
            No other thread can acquire either a read lock or a write lock.
        If any thread has acquired a read lock:
            No other thread can acquire a write lock.
            Other threads can still acquire a read lock.
*/
public class Thread_18_ReentrantReadWriteLock {

    static class SharedResource {
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        // Read Lock - multiple threads can read concurrently
        public void readResource(int readerId) {
                lock.readLock().lock();
                try {
                    System.out.println("Reader " + readerId + " is reading.");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Reader " + readerId + " finished reading.");
                } finally {
                    lock.readLock().unlock();
                }
        }

        // Write Lock - only one thread can write at a time
        public void writeResource(int writerId) {
                lock.writeLock().lock();
                try {
                    System.out.println("Writer " + writerId + " is writing.");
                    try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    System.out.println("Writer " + writerId + " finished writing.");
                } finally {
                    lock.writeLock().unlock();
                }
        }
    }

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        // Readers
        for (int i = 1; i <= 4; i++) {
            int readerId = i;
            new Thread(() -> sharedResource.readResource(readerId)).start();
        }

        // Writers
        for (int i = 1; i <= 2; i++) {
            int writerId = i;
            new Thread(() -> sharedResource.writeResource(writerId)).start();
        }
    }
}
