package ThreadPackage.src;

import java.util.concurrent.atomic.AtomicInteger;

public class Thread_10_AtomicOperation {

    public static void main(String[] args) {
        int j = 0;
        j++;
        /*
        The operation i++ is not atomic.
        Read: The current value of i is read from memory.
        Increment: The value is incremented by 1.
        Write: The new value is written back to memory.
        These steps can be interleaved by other threads, leading to potential race conditions.
        For example, if two threads try to increment i simultaneously, the final value might not be the expected sum of the increments.
        To ensure atomic operations on integer variables, you can use the AtomicInteger class from the java.util.concurrent.atomic package.
        This class provides methods like incrementAndGet() that are guaranteed to be atomic.
        */

        AtomicInteger x = new AtomicInteger(0);
        x.incrementAndGet();

        AtomicInteger counter = new AtomicInteger(0);
        // Multiple threads can safely increment the counter
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.incrementAndGet();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.incrementAndGet();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Final counter value: " + counter.get());
    }
    /*
    In this example, the incrementAndGet() method ensures that the increment operation is atomic,even in a multi-threaded environment.
    If the integer is not atomic, then there is a chance of unexpected results. Otherwise we need to make sure the code is synchronized.
    It's not a good idea to make all code synchronized. Bc it stops parallel execution.
    */

    /*
    ATOMIC OPERATIONS:
        All reference assignments are atomic
        We can get and set references to objects atomically
        Object a = new Object();
        Object b = new Object();
        a = b; // Atomic
        So getters and setters are atomic and no need to synchronize

        All assignments to primitive types are atomic except long and double
        Reading from and writing to types: int, short, byte, float, char, boolean are atomic and no need to synchronize.
        Long and Double are 64 bits hence it's not Atomic.

        If you declare long and double as Volatile, then the assignments to these variables are atomic
     */
}


