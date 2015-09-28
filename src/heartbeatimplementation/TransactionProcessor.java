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

/**
 *
 * @author neloh
 */
public class TransactionProcessor implements Runnable{
    int sendingInverval = 1000;
    int lastTransactionProcessedTime; 
    boolean isActive;
    Integer id=null;
    
    
    public static void main(String[] args) throws InterruptedException {
        TransactionProcessor transProcessor=new TransactionProcessor();
        new Thread(transProcessor).start();
        while(true){
            transProcessor.sendAliveSignal();
            Thread.sleep(transProcessor.sendingInverval);
        }
        
    }
    
    void processTransaction(){
       
    }
    
    void sendAliveSignal(){
        //InvokePitAPat in Fault detector
        try {
            RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
            if (id==null){
                id=obj.getId();
            }
            obj.pitAPat(id);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        processTransaction();
    }
}
