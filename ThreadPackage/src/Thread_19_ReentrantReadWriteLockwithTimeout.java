package ThreadPackage.src;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Thread_19_ReentrantReadWriteLockwithTimeout {

    static class SharedResource {
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true); // fair lock

        // Read Lock - multiple threads can read concurrently
        public void readResource(int readerId, long timeout, TimeUnit unit) {
            try {
                // Attempt to acquire the read lock with timeout
                if (lock.readLock().tryLock(timeout, unit)) {
                    try {
                        System.out.println("Reader " + readerId + " is reading.");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.println("Reader " + readerId + " finished reading.");
                    } finally {
                        lock.readLock().unlock();
                    }
                } else {
                    System.out.println("Reader " + readerId + " could not acquire lock within timeout.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Write Lock - only one thread can write at a time
        public void writeResource(int writerId, long timeout, TimeUnit unit) {
            try {
                // Attempt to acquire the write lock with timeout
                if (lock.writeLock().tryLock(timeout, unit)) {
                    try {
                        System.out.println("Writer " + writerId + " is writing.");
                        try { Thread.sleep(200);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.println("Writer " + writerId + " finished writing.");
                    } finally {
                        lock.writeLock().unlock();
                    }
                } else {
                    System.out.println("Writer " + writerId + " could not acquire lock within timeout.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        long timeout = 500; // Timeout in milliseconds

        // Readers
        for (int i = 1; i <= 4; i++) {
            int readerId = i;
            new Thread(() -> sharedResource.readResource(readerId, timeout, TimeUnit.MILLISECONDS)).start();
        }

        // Writers
        for (int i = 1; i <= 2; i++) {
            int writerId = i;
            new Thread(() -> sharedResource.writeResource(writerId, timeout, TimeUnit.MILLISECONDS)).start();
        }
    }
}

