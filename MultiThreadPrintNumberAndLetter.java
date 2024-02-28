public class MultiThreadPrintNumberAndLetter {
   static int start = 1;
   static int end = 26;
   static char first = 'A';
   static char last = 'Z';
   static boolean isChar = false;

   public void printNumbers(){
       synchronized (this){
           while(start<=end){
               while(isChar){
                   try{
                       wait();
                   }catch (Exception e){
                   }
               }
               System.out.println(start);
               start++;
               isChar = !isChar;
               notify();
           }
       }
   }

    public void printCharacter(){
        synchronized (this){
            while(first<=last){
                while(!isChar){
                    try{
                        wait();
                    }catch (Exception e){
                    }
                }
                System.out.println(first);
                first++;
                isChar = !isChar;
                notify();
            }
        }
    }

    public void print(){
       Runnable r1 = new Runnable(){
           @Override
           public void run(){
               printNumbers();
           }
       };
        Runnable r2 = new Runnable(){
            @Override
            public void run(){
                printCharacter();
            }
        };
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
    }

    public static void main(String[] args){
       MultiThreadPrintNumberAndLetter multiThread3 = new MultiThreadPrintNumberAndLetter();
       multiThread3.print();
    }
}
