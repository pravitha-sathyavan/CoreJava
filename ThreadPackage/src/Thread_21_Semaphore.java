package ThreadPackage.src;

import java.util.concurrent.Semaphore;

public class Thread_21_Semaphore {
    /*
    Can be used to restrict number of users to a particular resource or group of resources.
    Unlike the lock that allows only one user per resource.


    Eg: In a parking lot with 8 slots, the maximum no of cars allowed to park = 8
        Using 8 semaphores we can implement this scenario

    Semaphore vs Lock
        Semaphore don't have a notion of owner thread
        Many threads can acquire a permit
        The same thread can acquire the semaphore multiple times
        The binary thread (initialized with 1) permit is not reentrant.
        Semaphore can be release by any thread.
        It can be released by any thread who hasn't acquired it as well.

    */
    private void semaphoreFn() throws InterruptedException {
        int NO_OF_SEMAPHORE = 10;
        Semaphore semaphore = new Semaphore(NO_OF_SEMAPHORE); // Sets no of permits to NO_OF_SEMAPHORE
        semaphore.acquire(1); // Acquires one semaphore
        semaphore.acquire(3); // Acquires three semaphores
        // Use Semaphore
        semaphore.release(4); // Releases four semaphores

        semaphore.acquire(NO_OF_SEMAPHORE); // Acquires all semaphores
        semaphore.acquire(1); // Blocks thread since no semaphore is left
    }
}
