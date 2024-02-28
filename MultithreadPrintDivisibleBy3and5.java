public class MultithreadPrintDivisibleBy3and5 {
    static int start = 1;
    static int end = 30;

    public void printDivBy3DivBy5(){
        synchronized (this){
          while(start<=end){
              while(!(start%3==0&&start%5==0)) {
                  try {
                      wait();
                  } catch (Exception e) {
                  }
              }
              System.out.println("Thread 1 "+start);
              start++;
              notifyAll();
          }
        }
    }

    public void printDivBy3NotDivBy5(){
        synchronized (this){
            while(start<=end){
                while(!(start%3==0&&start%5!=0)) {
                    try {
                        wait();
                    } catch (Exception e) {
                    }
                }
                System.out.println("Thread 2 "+start);
                start++;
                notifyAll();
            }
        }

    }

    public void printNotDivBy3DivBy5(){
        synchronized (this){
            while(start<=end){
                while(!(start%3!=0&&start%5==0)) {
                    try {
                        wait();
                    } catch (Exception e) {
                    }
                }
                System.out.println("Thread 3 "+start);
                start++;
                notifyAll();
            }
        }
    }

    public void printNotDivBy3NotDivBy5(){
        synchronized (this){
            while(start<=end){
                while(!(start%3!=0&&start%5!=0)) {
                    try {
                        wait();
                    } catch (Exception e) {
                    }
                }
                System.out.println("Thread 4 "+start);
                start++;
                notifyAll();
            }
        }
    }

    public void print(){
        Runnable r1 = new Runnable(){
            @Override
            public void run(){
                printDivBy3DivBy5();
            }
        };
        Runnable r2 = new Runnable(){
            @Override
            public void run(){
                printDivBy3NotDivBy5();
            }
        };
        Runnable r3 = new Runnable(){
            @Override
            public void run(){
                printNotDivBy3DivBy5();
            }
        };
        Runnable r4 = new Runnable(){
            @Override
            public void run(){
                printNotDivBy3NotDivBy5();
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);
        Thread t4 = new Thread(r4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public static void main(String[] args){
        MultithreadPrintDivisibleBy3and5 multithread2 = new MultithreadPrintDivisibleBy3and5();
        multithread2.print();
    }
}
