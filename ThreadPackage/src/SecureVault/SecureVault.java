package ThreadPackage.src.SecureVault;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SecureVault {

    private static final Integer MAX_PASSWORD = 500;
    private static class Vault{
        private int password;

        public Vault(int password){
            this.password = password;
        }

        public boolean isCorrectPassword(int guess){
            try{
                Thread.sleep(5); // This is to delay in case if the hacker is trying to guess password
            } catch (InterruptedException e){
            }
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread{
        protected Vault vault;

        public HackerThread(Vault vault){
            this.vault = vault;
            this.setName(this.getClass().getName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start(){
            System.out.println("Started "+ this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread{
        public AscendingHackerThread(Vault vault){
            super(vault);
        }

        @Override
        public void run(){
            for(int guess=0 ; guess<MAX_PASSWORD ; guess++){
                if(vault.isCorrectPassword(guess)){
                    System.out.println("Ascending Hacker Thread guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread{
        public DescendingHackerThread(Vault vault){
            super(vault);
        }

        @Override
        public void run(){
            for(int guess = MAX_PASSWORD ; guess >= 0 ; guess--){
                if(vault.isCorrectPassword(guess)){
                    System.out.println("Descending Hacker Thread guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread{
        @Override
        public void run(){
            for(int i = 10; i > 0; i-- ){
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                }
                System.out.println(i);
            }
            System.out.println("Game over for hackers..");
            System.exit(0);
        }
    }

    public static void main(String[] args){
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
        List<Thread> threadList = new ArrayList<>();

        threadList.add(new AscendingHackerThread(vault));
        threadList.add(new DescendingHackerThread(vault));
        threadList.add(new PoliceThread());

        for(Thread thread:threadList){
            thread.start();
        }
    }

}
