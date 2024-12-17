package ThreadPackage.src;

public class Thread_2_InterruptMethod {

    public static void main(String[] args){
        Thread thread1 = new Thread(new BlockingTask());
        thread1.start();
        thread1.interrupt(); // This line gets executed bc the thread is handling InterruptedException

        Thread thread2 = new Thread(new LongTimeRunningThread());
        thread2.start();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread2.interrupt(); // This line gets executed bc of Thread.currentThread().isInterrupted() code
    }

    private static class BlockingTask implements Runnable{
        @Override
        public void run(){
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.out.println("Caught Interrupted Exception");
            }
        } // If we leave the catch block empty, then the interrupt() method wont stop the thread execution
    }

    private static class LongTimeRunningThread implements Runnable{
        @Override
        public void run(){
            System.out.println("Starting Thread...");
            fn();
        }

        public static void fn(){
            for(int i=0;;i++){
                System.out.println(i);
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Stopping Thread...");
                    return;
                }
            }
        }
    }
}


/*
Thread consumes CPU and memory even if it's not running
We need to clean up unused threads
Application will be stopped only if all of its threads are stopped


Ways to stops thread:
1. use interrupt() method
If the thread we are trying to interrupt is executing a method that throws InterruptedException
    eg. Thread.sleep() has to handle InterruptedException
If the thread we are trying to interrupt is handling the interrupt signal explicitely

      if(Thread.currentThread().isInterrupted()){
           System.out.println("Stopping Thread...");
           return;
     }
     If we remove the above code, Thread.interrupt() wont work.
 */
