package ThreadPackage.src;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Thread_11_MetricCalculation {

    private static final int NUM_THREADS = 4;
    private static final int NUM_ITERATIONS = 1000000;

    private static AtomicLong totalExecutionTime = new AtomicLong();
    private static AtomicLong minExecutionTime = new AtomicLong(Long.MAX_VALUE);
    private static AtomicLong maxExecutionTime = new AtomicLong(Long.MIN_VALUE);

    public static void main(String[] args) throws InterruptedException {

        // Create a thread pool with NUM_THREADS threads
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        // Submit tasks to the thread pool
        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(() -> {
                long startTime = System.nanoTime();

                // Run the code to be measured multiple times
                for (int j = 0; j < NUM_ITERATIONS; j++) {
                    // The code to be measured goes here
                }

                long endTime = System.nanoTime();
                long executionTime = endTime - startTime;

                // Update the total execution time
                totalExecutionTime.addAndGet(executionTime);

                // Update the min and max execution times
                minExecutionTime.compareAndSet(executionTime, Math.min(executionTime, minExecutionTime.get()));
                maxExecutionTime.compareAndSet(executionTime, Math.max(executionTime, maxExecutionTime.get()));
            });
        }

        // Wait for all tasks to complete
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

        // Calculate and print the average execution time
        long averageExecutionTime = totalExecutionTime.get() / (NUM_THREADS * NUM_ITERATIONS);
        System.out.println("Average execution time: " + averageExecutionTime + " ns");

        // Print the min and max execution times
        System.out.println("Min execution time: " + minExecutionTime + " ns");
        System.out.println("Max execution time: " + maxExecutionTime + " ns");
    }

    /*
Atomic Variables:
AtomicLong ensures thread-safe updates for shared metrics (totalExecutionTime, minExecutionTime, maxExecutionTime), when multiple threads are running concurrently.


Multiple threads are updating shared variables (totalExecutionTime, minExecutionTime, maxExecutionTime) simultaneously.
If regular variables (e.g., long) were used, updates could lead to race conditions, where incorrect or inconsistent values might be stored due to concurrent modifications.
Atomic variables ensure that updates are performed atomically, meaning that they cannot be interrupted by other threads.

Avoiding Explicit Synchronization:
Without atomic variables, you would need to use locks or synchronized blocks to ensure thread-safe updates.
These mechanisms introduce additional complexity and may reduce performance.
Atomic variables provide built-in thread-safe methods for common operations like addition (addAndGet()), comparison, and setting (compareAndSet()).

To make regular variables thread-safe, you would need explicit locking:
synchronized(lock) {
    totalExecutionTime += executionTime;
}
This adds overhead and complicates the code.

Benefits of Using Atomic Variables
Thread-Safety: Ensures that metrics are correctly updated in a multithreaded environment.
Simplicity: No need for explicit locks or synchronization blocks.
Performance: Atomic operations are faster than using locks since they avoid context switching.
Scalability: The program can handle more threads without contention issues.

    */
}