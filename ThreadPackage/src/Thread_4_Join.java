package ThreadPackage.src;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Different threads run independently
Order of execution of threads is out of our control
If we have two threads A and B, using join() method we can make sure that thread A gets executed completely before thread B
 */

public class Thread_4_Join {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Case 1....");
        List<Long> inputNumbers1 = Arrays.asList(0L, 3435L, 1234L, 22L, 77777L);
        List<FactorialThread> factorialThreads1 = new ArrayList<>();
        for(Long inputNumber: inputNumbers1){
            factorialThreads1.add(new FactorialThread(inputNumber));
        }
        for(FactorialThread factorialThread: factorialThreads1){
            factorialThread.start();
        }
        for(FactorialThread factorialThread: factorialThreads1){
            if(factorialThread.isCalculated()){
                System.out.println("Completed processing of "+ factorialThread.inputNumber + " with output: " + factorialThread.result);
            }
            else{
                System.out.println("Not Completed processing of "+ factorialThread.inputNumber );
            }
        }
        /*
        In this case there is a race condition between main thread and factorial threads.
        The main thread won't wait in above case for other threads to get complete.
        if we want the main thread to wait for any other thread we should use join method
        eg: factorialThreads.get(0).join(); -> In this case main thread waits for first thread in the list.
        */

        System.out.println("Case 2....");
        List<Long> inputNumbers2 = Arrays.asList(0L, 3435L, 1234L, 22L, 77777L);
        List<FactorialThread> factorialThreads2 = new ArrayList<>();
        for(Long inputNumber: inputNumbers2){
            factorialThreads2.add(new FactorialThread(inputNumber));
        }

        for(FactorialThread factorialThread: factorialThreads2){
            factorialThread.start();
        }
        for(FactorialThread factorialThread: factorialThreads2){
            factorialThread.join(); // This line makes sure the main thread waits for factorialThread
        }

        for(FactorialThread factorialThread: factorialThreads2){
            if(factorialThread.isCalculated()){
                System.out.println("Completed processing of "+ factorialThread.inputNumber + " with output: " + factorialThread.result.bitCount());
            }
            else{
                System.out.println("Not Completed processing of "+ factorialThread.inputNumber );
            }
        }

        System.out.println("Case 3....");
        List<Long> inputNumbers3 = Arrays.asList(100000000000L, 3435L, 1234L, 22L, 77777L);
        List<FactorialThread> factorialThreads3 = new ArrayList<>();
        for(Long inputNumber: inputNumbers3){
            factorialThreads3.add(new FactorialThread(inputNumber));
        }

        for(FactorialThread factorialThread: factorialThreads3){
            factorialThread.setDaemon(true); // If we set the thread as daemon then the thread will stop when the main fn gets completed
            factorialThread.start();
        }
        for(FactorialThread factorialThread: factorialThreads3){
            factorialThread.join(20000); // This line makes sure the main thread waits for factorialThread but a maximum of 2 seconds
        }

        for(FactorialThread factorialThread: factorialThreads3){
            if(factorialThread.isCalculated()){
                System.out.println("Completed processing of "+ factorialThread.inputNumber + " with output: " + factorialThread.result.bitCount());
            }
            else{
                System.out.println("Not Completed processing of "+ factorialThread.inputNumber );
            }
        }
        System.out.println("Completed...");
    }


    private static class FactorialThread extends Thread{
        private Long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isCalculated = false;

        public BigInteger getResult() {
            return result;
        }

        public boolean isCalculated() {
            return isCalculated;
        }

        public FactorialThread(Long inputNumber){
            this.inputNumber = inputNumber;
        }

        @Override
        public void run(){
            result = calculateFactorial(inputNumber);
            isCalculated = true;
        }

        private BigInteger calculateFactorial(Long inputNumber) {
            BigInteger tempResult = BigInteger.ONE;
            for(int i=1;i<=inputNumber;i++){
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }
    }
}



