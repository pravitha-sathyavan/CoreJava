package ThreadPackage.src;

public class Thread_7_SynchronisedMethod {

        // Shared counter variable
        private int counter = 0;

        // Synchronized method to increment the counter
        public synchronized void increment() {
            counter++;
            System.out.println(Thread.currentThread().getName() + " incremented counter to: " + counter);
        }

        // Synchronized method to decrement the counter
        public synchronized void decrement() {
            counter--;
            System.out.println(Thread.currentThread().getName() + " decremented counter to: " + counter);
        }

        // Main method to demonstrate the functionality
        public void main(String[] args) {
            Thread_7_SynchronisedMethod counterObject = new Thread_7_SynchronisedMethod();

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

    /*
    If the synchronized keyword were removed from the increment and decrement methods, the program would no longer guarantee thread safety, leading to potential race conditions.
    Multiple threads could access the counter variable at the same time. This can result in overlapping read-modify-write operations, where the changes from one thread overwrite those of another.

    Without synchronization, a thread may read a stale value of counter while another thread is modifying it.
    For example, both threads could read the same initial value of counter, increment or decrement it, and then write their results, effectively losing one of the updates.

    Since the threads are working concurrently without proper synchronization, the final value of counter may not reflect all increments and decrements performed by the threads.
    This is a classic issue in multi-threaded programming and highlights the importance of ensuring thread safety when working with shared resources.
     */
