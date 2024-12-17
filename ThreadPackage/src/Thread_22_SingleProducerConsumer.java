package ThreadPackage.src;

import java.util.concurrent.Semaphore;

public class Thread_22_SingleProducerConsumer {
    private static int buffer; // Shared variable between producer and consumer

    // Semaphores
    private static Semaphore empty = new Semaphore(1);
    private static Semaphore full = new Semaphore(0); // The thread trying to acquire it gets blocked since no of permits is zero.
    /*
    The 0 passed to the constructor of Semaphore sets the number of available permits to 0 initially.
    It does not mean the capacity is 0. Instead: The Semaphore starts with 0 permits available.
    Any thread that tries to acquire a permit will block because no permits are initially available.

    The method full.release() adds a permit to the semaphore.
    This increases the count of permits available for other threads to acquire.
    When you release the semaphore:
         If there is a blocked thread waiting on acquire(), one of those threads will be unblocked.
         If no thread is waiting, the permit will stay available for future acquisitions

    In a Producer-Consumer setup:
    The Producer calls full.release() after producing an item. This makes a permit available.
    The Consumer calls full.acquire() before consuming. This blocks the consumer until the producer releases the permit.

    A thread trying to acquire a semaphore with no permits available will block (sleep).
    It will wake up automatically when another thread calls release() to add a permit.
    This behavior ensures proper thread synchronization and avoids busy waiting.
    */

    // Producer class
    static class Producer extends Thread {
        @Override
        public void run() {
            int i=1;
            try {
                while(true){
                    empty.acquire(); // Producer acquires empty semaphore, produces item and releases a semaphore in full.
                    // The producer is not able to produce more than one item bc it acquires empty semaphore and this has to be releases by consumer to produce item next
                    buffer = i;
                    System.out.println("Producer produced: " + buffer);
                    full.release();
                    Thread.sleep(1000); // Simulate production time
                    i++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Consumer class
    static class Consumer extends Thread {
        @Override
        public void run() {
            try {
                while(true){
                    full.acquire(); // Tries to acquire full semaphore but gets blocked initially.
                    // When producer releases a semaphore in full, it will acquire it and consumes the shared item and releases the empty
                    System.out.println("Consumer consumed: " + buffer);
                    empty.release();
                    Thread.sleep(1000); // Simulate consumption time
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        producer.start();
        consumer.start();
    }
}
