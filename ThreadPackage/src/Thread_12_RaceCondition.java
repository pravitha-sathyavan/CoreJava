package ThreadPackage.src;

public class Thread_12_RaceCondition {
    /*
    A race condition occurs when multiple threads access a shared resource.
    At least one thread is modifying the value of shared resource.
    The timing or order of thread's scheduling may cause incorrect results.
    Non-atomic operation is getting executed in shared resource.

    The solution to this problem is to identify the code that causes the race condition and protect it as a synchronized block.
    */
    public static class SharedVariable {
        int x = 0;
        int y = 0;

        public void incrementData() {
            x++;
            y++;
        }

        public void comapreData() {
            if (y > x) {
                System.out.println("Data race occured with y>x " + y + " " + x);
            }
        }
    }

    public static void main(String[] args){
        SharedVariable sharedVariable = new SharedVariable();
        Thread thread1 = new Thread(()->{
            while(true) {
                sharedVariable.incrementData();
            }
        });
        Thread thread2 = new Thread(()->{
            while(true) {
                sharedVariable.comapreData();
            }
        });
        thread1.start();
        thread2.start();
    }
    /*
    DataRace
    In the above code, there are chances where the below line gets executed
      System.out.println("Data race occured with y>x " + y + " " + x);
    This is bc:
    In modern CPUs and compilers, operations may be reordered for optimization.
    They execute instructions out of order inorder to improve performance and utilization.
    They will do so while maintaining logical correctness of code.

    x = 2
    y = x+3
    z = y-5
    There won't be any data race in above code snippet bc every line depends on previous line's result.

    x++;
    y++;
    There will be reordering in above code since both lines are independent.
    If it's a single thread, there won't be any issue. In multithreaded env there will be data race.

    Without synchronization, the updates to x and y may not happen in the expected order in memory.
    This can result in y being incremented and visible to thread2 before x is incremented, even though incrementData is executed sequentially.

    Solutions:
    1. Use synchronized block
    2. Declare a variable as volatile.
        If we declare a variable as volatile, all instructions before read/write a volatile variable gets executed before that line
        and all instructions after read/write volatile variable executes after that. i.e ordering is maintained.
    */

}
