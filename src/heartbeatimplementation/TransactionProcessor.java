/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeatimplementation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author neloh
 */
public class TransactionProcessor implements Runnable{
    static int sendingInverval = 1000;
    int lastTransactionProcessedTime; 
    boolean isActive;
    Integer id=null;
    
    int numberOfAccounts = 1000;
    ArrayList<Integer> accounts = new ArrayList<>();
    ArrayList<Integer> divisors = new ArrayList<>();
    Random r = new Random();
    
    public static void main(String[] args) throws InterruptedException {
        TransactionProcessor transProcessor=new TransactionProcessor();
        Thread t=new Thread(transProcessor);
        t.setUncaughtExceptionHandler(transProcessor.h);
        t.start();
        while(true){
            transProcessor.sendAliveSignal();
            Thread.sleep(transProcessor.sendingInverval);
        }
        
    }
    
    Thread.UncaughtExceptionHandler h= new Thread.UncaughtExceptionHandler(){
        public void uncaughtException(Thread th, Throwable ex) {
         System.exit(1);
        }
    };
       
    TransactionProcessor(){
        //Fill accounts and divisors
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i=0; i<numberOfAccounts; i++){
            Integer r = rand.nextInt() % 256;
            accounts.add(r);
        }

        for (int i=0; i<numberOfAccounts; i++){
            Integer r = rand.nextInt(100);
            divisors.add(r);
        }
    }
    
    void processTransaction() throws InterruptedException{
        r.setSeed(System.currentTimeMillis());
//        int x=r.nextInt(100);
//        while(x<2){
//            Thread.sleep(1000);
//        }
        for(Integer accountBal: accounts){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
            Integer divisor = r.nextInt(100);
            int result= accountBal/divisor;
             System.out.println("Transaction Processed: Result ="+ result );
        }
        
    }
    
    void sendAliveSignal(){
        //InvokePitAPat in Fault detector
        try {
            RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");

            if (id==null){
                id=obj.getId();
            }
            obj.pitAPat(id);
            System.out.println("Beat");

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            processTransaction();
        } catch (InterruptedException ex) {
            Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
