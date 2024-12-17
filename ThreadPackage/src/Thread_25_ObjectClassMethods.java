package ThreadPackage.src;

public class Thread_25_ObjectClassMethods {
/*
Object class contains following methods:
    wait() - causes current thread to wait until another thread wakes it up. The thread don't consume any CPU.
    notify() - wakes up a single thread waiting on the object
    notifyAll() - wakes up all threads waiting on the object
Every java class inherits from object class
We can use any object as a condition variable and a lock (using synchronised keyword)
*/

    private boolean ready = false;

    // Method where the thread waits
    public synchronized void waitForSignal() {
        try {
            while (!ready) { // Wait until ready becomes true
                System.out.println(Thread.currentThread().getName() + " is waiting...");
                wait(); // Thread goes to waiting state
            }
            System.out.println(Thread.currentThread().getName() + " received the signal!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to send the signal
    public synchronized void sendSignal() {
        System.out.println(Thread.currentThread().getName() + " is sending the signal...");
        ready = true; // Change the condition
        //notify(); // Notify one waiting thread
        // Uncomment the following line to notify all threads:
         notifyAll();
    }


        public static void main(String[] args) {
            Thread_25_ObjectClassMethods resource = new Thread_25_ObjectClassMethods();

            // Thread 1: Waits for a signal
            Thread waitingThread1 = new Thread(() -> resource.waitForSignal(), "WaitingThread1");

            // Thread 2: Waits for a signal
            Thread waitingThread2 = new Thread(() -> resource.waitForSignal(), "WaitingThread2");

            // Thread 3: Sends a signal
            Thread signalingThread = new Thread(() -> {
                try {
                    Thread.sleep(2000); // Simulate some work before sending the signal
                    resource.sendSignal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "SignalingThread");

            // Start the threads
            waitingThread1.start();
            waitingThread2.start();
            signalingThread.start();
        }


}