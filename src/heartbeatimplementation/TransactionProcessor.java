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
    int numberOfAccounts = 1000;
    ArrayList<Integer> accounts = new ArrayList<>();
    ArrayList<Integer> divisors = new ArrayList<>();
    Random r = new Random();
    
    public static void main(String[] args) throws InterruptedException {
        TransactionProcessor transProcessor=new TransactionProcessor();
        new Thread(transProcessor).start();
        while(true){
            transProcessor.sendAliveSignal();
            Thread.sleep(transProcessor.sendingInverval);
        }
        
    }
       
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
    
    void processTransaction(){
        r.setSeed(System.currentTimeMillis());
        
        for(Integer accountBal: accounts){
            Integer divisor = r.nextInt(2000);
            int result = accountBal/divisor;
             System.out.println("Transaction Processed: Result ="+ result);
        }
        
    }
    
    void sendAliveSignal(){
        //InvokePitAPat in Fault detector
        try {
            RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
            obj.pitAPat();
            System.out.println("Beat");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        processTransaction();
    }
}
