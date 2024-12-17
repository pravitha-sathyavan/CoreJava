package ThreadPackage.src;

public class Thread_3_DaemonThread {
    public static void main(String[] args){

        Thread thread2 = new Thread(new LongTimeRunningThread());
        thread2.setDaemon(true);
        thread2.start();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread2.interrupt(); // This line gets executed bc of thread2.setDaemon(true);
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
//                if(Thread.currentThread().isInterrupted()){
//                    System.out.println("Stopping Thread...");
//                    return;
//                }
            }
        }
    }

}


/*
Usually threads that run in background prevent the application to exit if the main thread terminates.
Daemon Thread are threads that run in background that do not prevent the application to exit if the main thread terminates.

Scenarios where we need to create thread as Daemon:
    1. Background tasks that should not block our application from terminating
    eg: File saving thread in a text editor should not stop application to exit

    2. Code in a worker thread is not under our control, and we do not want it to block our application from terminating
    eg: Worker thread from external library that do not handle thread interrupt method
 */
