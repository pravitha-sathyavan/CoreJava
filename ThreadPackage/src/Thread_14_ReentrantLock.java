package ThreadPackage.src;/*
    Reentrant Lock
    Works just like the synchronized keyword applied on an object
    Requires explicit locking and unlocking
    Explicit unlocking has some disadvantages:
            If some exception is thrown between lock() and unlock(), then the unlock() wont get executed.
            To avoid this, the entire code bw lock() and unlock() should be put in try block and unlock() should be put in finally block.
            This makes sure that unlock() happens even if there is an exception in critical section or if there is a return() statement in critical section.
*/

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Thread_14_ReentrantLock {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    // Increment method using ReentrantLock
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
    /*
    public synchronized void increment() {
        count++;
    }
    */

    // Getter method using ReentrantLock
    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            // Even if there is a return statement, lock.unlock() gets executed since its placed in finally block.
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Thread_14_ReentrantLock counter = new Thread_14_ReentrantLock();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count (ReentrantLock): " + counter.getCount());
    }
}

