/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeatimplementation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author neloh
 */
public class FaultDetector extends UnicastRemoteObject implements RmiServerIntf, Runnable {
    static int checkingInterval = 15000; //in miliseconds
    long checkingTime=1500;
    static long expireTime;
    long lastUpdatedTime;
    boolean isAlive  = true;
    int lastId=0;
    HashMap<Integer, Long> lastUpdatedTimeMap=new HashMap<>();
    
    public FaultDetector() throws RemoteException {
        super(0);    // required to avoid the 'rmic' step
    }
    
    void checkAlive(){
        if(System.currentTimeMillis()  > expireTime){
            isAlive = false;
            System.out.println("isAlive=False");
            System.out.println(expireTime);
        }
            
        
        if(!isAlive){
             //RMI the fault monitor
            //Raise exception
        }
            
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        
        FaultDetector fl=new FaultDetector();
        new Thread(fl).start();
        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
           
        //Instantiate RmiServer

        FaultDetector obj = new FaultDetector();

        // Bind this object instance to the name "RmiServer"
        Naming.rebind("//localhost/RmiServer", obj);
        System.out.println("Fault Detector started");
        
        
    }

    @Override
    public void pitAPat(int id){
        System.out.println("pitAPat"+id);
        isAlive=true;
        updateTime(id);
    }

    void updateTime(int id){
        this.lastUpdatedTimeMap.put(id, System.currentTimeMillis());
        this.lastUpdatedTime = System.currentTimeMillis() ;
        this.expireTime = lastUpdatedTime + checkingInterval;
        System.out.println("expireTime"+expireTime);
    }

    @Override
    public int getId() throws RemoteException {
        lastId++;
        return lastId;
    }

    @Override
    public void run() {
        try {
            while(true){
                checkAlive();
                Thread.sleep(checkingTime);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(FaultDetector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
