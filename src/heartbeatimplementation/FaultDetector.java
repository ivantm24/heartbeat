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

/**
 *
 * @author neloh
 */
public class FaultDetector extends UnicastRemoteObject implements RmiServerIntf {
    int checkingInterval = 15000; //in miliseconds
    long checkingTime;
    long expireTime;
    long lastUpdatedTime;
    boolean isAlive  = true;
    
    public FaultDetector() throws RemoteException {
        super(0);    // required to avoid the 'rmic' step
    }
    
    void checkAlive(){
        if(lastUpdatedTime < expireTime)
            isAlive = false;
        
        if(!isAlive){
            //Raise exception
        }
            
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        

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
    public void pitAPat(){
        System.out.println("pitAPat");
        updateTime();
    }

    void updateTime(){
        this.lastUpdatedTime = System.currentTimeMillis() / 1000L;
        this.expireTime = lastUpdatedTime + checkingInterval;
    }

}