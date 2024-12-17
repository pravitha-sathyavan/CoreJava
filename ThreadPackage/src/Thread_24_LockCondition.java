package ThreadPackage.src;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread_24_LockCondition {
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static boolean ready = false;

    static class WaitingThread extends Thread {
        @Override
        public void run() {
            lock.lock(); // Acquire the lock
            try {
                while (!ready) {
                    System.out.println("Waiting for the signal...");
                    condition.await(); // Wait until signaled and unlocks the lock
                }
                System.out.println("Received the signal! Proceeding...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(); // Release the lock
            }
        }
    }

    static class SignalingThread extends Thread {
        @Override
        public void run() {
            lock.lock(); // Acquire the lock
            try {
                System.out.println("Preparing the signal...");
                Thread.sleep(2000); // Simulate some work
                ready = true;
                System.out.println("Sending the signal...");
                condition.signal(); // Signal the waiting thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(); // Release the lock
            }
        }
    }

    public static void main(String[] args) {
        WaitingThread waitingThread = new WaitingThread();
        SignalingThread signalingThread = new SignalingThread();

        waitingThread.start();
        signalingThread.start();
    }
}
/*
condition.signal();
Wakes up a single thread waiting on the condition variable.
A thread that wakes up has to reacquire the lock associated with the condition variable
If currently no thread is waiting on condition variable, the signal method do nothing
signalAll() -> Broadcasts a signal to all threads curretly waiting on condition variable
 */
