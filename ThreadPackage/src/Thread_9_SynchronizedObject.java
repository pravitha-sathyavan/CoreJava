package ThreadPackage.src;

public class Thread_9_SynchronizedObject {

    // Shared counter variable
    private int counter = 0;

    // Object to use as a lock, this can be any object.
    private final Object lock = new Object();

    // Method to increment the counter using synchronized block
    public void increment() {
        synchronized (lock) {
            counter++;
            System.out.println(Thread.currentThread().getName() + " incremented counter to: " + counter);
        }
    }

    // Method to decrement the counter using synchronized block
    public void decrement() {
        synchronized (lock) {
            counter--;
            System.out.println(Thread.currentThread().getName() + " decremented counter to: " + counter);
        }
    }

    // Main method to demonstrate the functionality
    public static void main(String[] args) {
        Thread_9_SynchronizedObject counterObject = new Thread_9_SynchronizedObject();

        // Runnable task to increment the counter
        Runnable incrementTask = () -> {
            for (int i = 0; i < 5; i++) {
                counterObject.increment();
                try {
                    Thread.sleep(100); // Simulating work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Runnable task to decrement the counter
        Runnable decrementTask = () -> {
            for (int i = 0; i < 5; i++) {
                counterObject.decrement();
                try {
                    Thread.sleep(100); // Simulating work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Create threads for both tasks
        Thread thread1 = new Thread(incrementTask, "Increment-Thread");
        Thread thread2 = new Thread(decrementTask, "Decrement-Thread");

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for threads to complete
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final counter value: " + counterObject.counter);
    }

    public void someMethod() {
        Object lock1 = new Object();
        Object lock2 = new Object();

        // Synchronized block using lock1
        synchronized (lock1) {
            System.out.println("Block synchronized on lock1");
            // Critical section for lock1
        }

        // Synchronized block using lock2
        synchronized (lock2) {
            System.out.println("Block synchronized on lock2");
            // Critical section for lock2
        }
    }

    /*
    Separate Locks: Each synchronized block acquires a lock on its respective object (lock1 or lock2).
    Threads accessing different synchronized blocks (using different locks) can execute them concurrently, as they don’t interfere with each other.
    If multiple threads need to synchronize on the same lock object, they will block each other when attempting to enter a synchronized block locked on that object.
     */

    public class Example {

        public synchronized void synchronizedMethod() {
            // Entire method synchronized on this
            System.out.println("Synchronized method");
        }

        public void blockSynchronizedMethod() {
            // Partially synchronized method
            System.out.println("Non-synchronized code");

            synchronized (this) {
                System.out.println("Synchronized block");
            }

            System.out.println("Non-synchronized code again");
        }

        /*
        Declaring a method synchronized will synchronize the entire method on the instance of the object (i.e., this).
        You can achieve the same behavior, but with finer control, by synchronizing specific blocks of code within the method.
        When to Use Which?
        synchronized Method:
        Use this when the entire method needs to be thread-safe and the logic is simple.
        synchronized(this) Block:
        Use this for finer control, especially if only a specific part of the method needs synchronization.
        */
    }

}

