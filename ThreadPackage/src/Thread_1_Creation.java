package ThreadPackage.src;

public class Thread_1_Creation {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
               System.out.println(Thread.currentThread().getName()); //Prints 'Thread-0' if name is not set, otherwise prints 'Worker Thread1'
            }
        });
        System.out.println(Thread.currentThread().getName()); // Prints main
        Thread.sleep(10000); // CPU sleeps during this time
        thread1.setName("Worker Thread1");
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread1.start();
        System.out.println(Thread.currentThread().getName()); // Prints main

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Intentional Exception");
            }
        });
        thread2.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Handling uncaught exceptions from thread2"); // Get called when an exception was thrown and not caught anywhere
            }
        });
        thread2.start();

        Thread thread3 = new NewThread();
        thread3.start();
    }

    private static class NewThread extends Thread{
        @Override
        public void run(){
            System.out.println("Hello from " + Thread.currentThread().getName());
        }
    }
}
