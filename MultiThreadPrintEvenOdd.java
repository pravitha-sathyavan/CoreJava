
public class MultiThreadPrintEvenOdd {
    static int start = 1;
    static int end = 15;

    public void printOddNumbers(){
        synchronized (this){
            while(start<end){
                while(start%2==0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Thread1 " + start);
                start++;
                notifyAll();
            }
        }
    }

    public void printEvenNumbers(){
        synchronized (this){
            while(start<end){
                while(start%2==1) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Thread2 " + start);
                start++;
                notifyAll();
            }
        }
    }
//    class Thread1 extends Thread{
//        @Override
//        public void run(){
//          printOddNumbers();
//        }
//    }
//
//    class Thread2 extends Thread{
//        @Override
//        public void run(){
//           printEvenNumbers();
//        }
//    }

    public void print(){
//        Thread t1 = new Thread1();
//        Thread t2 = new Thread2();
//        t1.start();
//        t2.start();

        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                printOddNumbers();
            }
        };

        Runnable t2= new Runnable() {
            @Override
            public void run() {
                printEvenNumbers();
            }
        };

        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);
        thread1.start();
        thread2.start();
    }

    public static void main(String[] args){
        MultiThreadPrintEvenOdd multiThread = new MultiThreadPrintEvenOdd();
        multiThread.print();
    }
}
