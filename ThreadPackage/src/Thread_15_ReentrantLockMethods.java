package ThreadPackage.src;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Thread_15_ReentrantLockMethods {

    private final ReentrantLock lock = new ReentrantLock(); // ReentrantLock instance
    private int counter = 0;

    public void lockAndIncrement() {
        lock.lock(); // Acquire lock
        try {
            System.out.println(Thread.currentThread().getName() + " acquired lock.");
            counter++;
            System.out.println("Counter: " + counter);
            System.out.println("Hold Count: " + lock.getHoldCount());
            Thread.sleep(1000); // Simulating work
            int queuedThreads = lock.getQueueLength();
            System.out.println("Threads waiting for the lock: " + queuedThreads);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock(); // Release lock
            System.out.println(Thread.currentThread().getName() + " released lock.");
        }
    }

    public void tryLockDemo() {
        if (lock.tryLock()) { // Attempt to acquire lock without waiting
            try {
                System.out.println(Thread.currentThread().getName() + " acquired lock using tryLock().");
                counter++;
                System.out.println("Counter: " + counter);
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " released lock.");
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " could not acquire lock using tryLock().");
        }
    }

    public void tryLockWithTimeoutDemo() {
        try {
            if (lock.tryLock(2, TimeUnit.SECONDS)) { // Try to acquire lock within 2 seconds
                try {
                    System.out.println(Thread.currentThread().getName() + " acquired lock with tryLock(timeout).");
                    counter++;
                    System.out.println("Counter: " + counter);
                } finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + " released lock.");
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could not acquire lock within timeout.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void lockStatusDemo() {
        System.out.println("Is lock currently held? " + lock.isLocked());
        System.out.println("Is lock held by current thread? " + lock.isHeldByCurrentThread());
        System.out.println("Queue length: " + lock.getQueueLength());
    }

    public static void main(String[] args) {
        Thread_15_ReentrantLockMethods demo = new Thread_15_ReentrantLockMethods();

        // Demonstrate lock() and unlock()
        Thread thread1 = new Thread(() -> {
            demo.lockAndIncrement();
            demo.lockStatusDemo();
        }, "Thread-1");

        // Demonstrate tryLock()
        Thread thread2 = new Thread(demo::tryLockDemo, "Thread-2");

        // Demonstrate tryLock with timeout
        Thread thread3 = new Thread(demo::tryLockWithTimeoutDemo, "Thread-3");

        thread1.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {

        }

        thread2.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {}

        thread3.start();

        // Wait for threads to complete
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final lock status
        demo.lockStatusDemo();

    }

}

/*
tryLock() Method in ReentrantLock
The tryLock() method in Java's ReentrantLock allows a thread to attempt to acquire a lock without blocking indefinitely.
This method is particularly useful when you want to avoid waiting for the lock to become available.
    eg: Image/Video processing
        High speed/ low latency trading platforms

Unlike lock(), which waits indefinitely for the lock, tryLock() immediately attempts to acquire the lock.
It returns immediately with a result.
Return Value:
    true: The lock was successfully acquired.
    false: The lock was not acquired because it was already held by another thread.
There is also an overloaded version of tryLock that waits for a specific duration before giving up:

boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException;

When to Use tryLock()
    When you want to perform non-blocking operations. For example:
    Avoid getting stuck waiting for a lock.

*/


