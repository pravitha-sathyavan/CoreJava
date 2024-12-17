package ThreadPackage.src;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Thread_23_MultipleProducerConsumer {
    private static final int MAX_CAPACITY = 5;

    // Semaphores
    private static Semaphore empty = new Semaphore(MAX_CAPACITY);
    private static Semaphore full = new Semaphore(0);
    private static Queue<Integer> queue = new ArrayDeque<>();
    private static ReentrantLock lock = new ReentrantLock();

    // Producer class
    static class Producer extends Thread {
        @Override
        public void run() {
            int i=1;
            try {
                while(true){
                    empty.acquire();
                    lock.lock();
                    queue.add(i);
                    System.out.println("Producer produced: " + i);
                    System.out.println(queue.size());
                    lock.unlock();
                    full.release();
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
                    full.acquire();
                    lock.lock();
                    int item = queue.poll();
                    System.out.println("Consumer consumed: " + item);
                    System.out.println(queue.size());
                    lock.unlock();
                    empty.release();
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
