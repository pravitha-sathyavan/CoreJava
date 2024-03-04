import java.util.LinkedList;

public class ProducerConsumer {
    static LinkedList<Integer> list;

    ProducerConsumer(){
        this.list = new LinkedList<>();
    }

    public void produce(){
        while(true){
            synchronized (this){
                while (!(list.size() < 3)) {
                    try {
                        wait();
                    } catch (Exception d) {

                    }
                }
                list.add(1);
                System.out.println("Producer" + list.size());
                notifyAll();
            }
        }
    }

    public void consume(){
        while(true){
            synchronized (this){
                while (!(list.size() > 0)) {
                    try {
                        wait();
                    } catch (Exception d) {

                    }
                }
                list.remove();
                System.out.println("Consumer" + list.size());
                notifyAll();
            }
        }
    }

    public void print() throws InterruptedException {
        Runnable r1 = new Runnable(){
            @Override
            public void run(){
                produce();
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                consume();
            }
        };
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

    }

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        producerConsumer.print();
    }
}
