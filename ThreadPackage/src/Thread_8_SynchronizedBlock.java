package ThreadPackage.src;

public class Thread_8_SynchronizedBlock {

    // Shared counter variable
    private int counter = 0;

    // Object to use as a lock
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
        Thread_8_SynchronizedBlock counterObject = new Thread_8_SynchronizedBlock();

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
}
