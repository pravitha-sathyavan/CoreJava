package ThreadPackage.src;
    /*
    Deadlock is situation that occurs when we have multiple locks.
    And every lock is waiting for another to gets complete

   Conditions of Deadlock:
   Deadlock is a condition in concurrent systems (such as operating systems, databases, or distributed systems) where a set of processes cannot proceed because each is waiting for a resource held by another process in the set.
   The four necessary conditions for a deadlock to occur are:

    1. Mutual Exclusion
    At least one resource must be held in a non-shareable mode, meaning only one process can use the resource at a time.
    If another process requests the resource, it must wait until the resource is released.
    2. Hold and Wait
    A process is holding at least one resource and waiting to acquire additional resources that are currently held by other processes.
    3. No Preemption
    Resources cannot be forcibly taken from a process holding them. A resource can only be released voluntarily by the process holding it, after it has completed its task.
    4. Circular Wait
    A set of processes is waiting on each other in a circular chain, where each process holds a resource that the next process in the chain needs.
    If all these conditions are true simultaneously, a deadlock occurs.

    Preventing or mitigating deadlocks involves breaking at least one of these conditions, such as:
        Eliminating mutual exclusion by allowing shared access to resources where possible.
        Avoiding hold and wait by requiring processes to request all resources at once.
        Allowing preemption of resources where feasible.
        Preventing circular wait by imposing an ordering on resource acquisition.

    The easiest solution is Preventing circular wait by imposing an ordering on resource acquisition and maintaining that order throughout the program.
    Other ways to prevent deadlock:
        Use watchdog for deadlock detection
        Use trylock method where we check if a lock is already acquired by another thread (Not possible with synchronised)

    THREAD1
    lock(A)
        lock(B)
            delete(item,B)
            add(item,A)
        unlock(B)
    unlock(A)

    THREAD2
    lock(B)
        lock(A)
            delete(item,A)
            add(item,B)
        unlock(A)
    unlock(B)

   Thread1 - lock(A) -- executed
   Thread2 - lock(B) -- executed
   Thread1 - lock(B) -- Not happening and waiting for release of lock B from Thread2
   Thread2 - lock(A) -- Not happening and waiting for release of lock A from Thread1
   This is how deadlock occurs
   */

public class Thread_13_DeadLock {

    // Shared resources representing the roads
    private static final Object ROAD_A = new Object();
    private static final Object ROAD_B = new Object();

    public static void main(String[] args) {
        // Creating two threads for two trains
        Thread train1 = new Thread(new Train("Train 1", ROAD_A, ROAD_B));
        Thread train2 = new Thread(new Train("Train 2", ROAD_B, ROAD_A));

        // Start both trains
        train1.start();
        train2.start();
    }

    static class Train implements Runnable {
        private final String name;
        private final Object firstRoad;
        private final Object secondRoad;

        public Train(String name, Object firstRoad, Object secondRoad) {
            this.name = name;
            this.firstRoad = firstRoad;
            this.secondRoad = secondRoad;
        }

        @Override
        public void run() {
            try {
                // Attempt to acquire the first road
                synchronized (firstRoad) {
                    System.out.println(name + " acquired " + roadName(firstRoad));

                    // Attempt to acquire the second road
                    synchronized (secondRoad) {
                        System.out.println(name + " waiting for " + roadName(secondRoad));
                        // Simulate some processing
                        Thread.sleep(100);
                    }
                }
            } catch (InterruptedException e) {
                System.err.println(name + " was interrupted.");
            }
        }

        private String roadName(Object road) {
            return road == ROAD_A ? "Road A" : "Road B";
        }
    }

    /*
    Each train attempts to acquire one road and then block another, resulting in a deadlock situation:

    Shared Resources (ROAD_A and ROAD_B):
    These represent the two roads that both trains need access to.
    Train Class:
    Each train attempts to acquire its first road and then the second road.
    The order of acquisition is different for each train, leading to a deadlock scenario.

    Deadlock:
    Train 1 locks ROAD_A and waits for ROAD_B, while Train 2 locks ROAD_B and waits for ROAD_A.
    Neither can proceed, resulting in a deadlock.

    How to Resolve Deadlock:
    To resolve this, you can implement a consistent resource acquisition order or use a timeout mechanism to detect
    and handle deadlocks.

    To avoid deadlock remove the circular dependency by maintaining order

     @Override
        public void run() {
            try {
                // Attempt to acquire the first road
                synchronized (ROAD_A) {
                    System.out.println(name + " acquired " + roadName(ROAD_A));

                    // Attempt to acquire the second road
                    synchronized (ROAD_B) {
                        System.out.println(name + " waiting for " + roadName(ROAD_B));
                        // Simulate some processing
                        Thread.sleep(100);
                    }
                }
            } catch (InterruptedException e) {
                System.err.println(name + " was interrupted.");
            }
        }

     */



}
