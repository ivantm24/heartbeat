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
public class FaultMonitor extends UnicastRemoteObject implements RmiFaultMonitorIntf{
    
    public FaultMonitor() throws RemoteException{
        super(0);    // required to avoid the 'rmic' step
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        try { 
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            System.out.println("java RMI registry already exists.");
        }
           
        //Instantiate RmiServer
        FaultMonitor obj = new FaultMonitor();

        // Bind this object instance to the name 
        Naming.rebind("//localhost/RmiMonitor", obj);
        System.out.println("Fault Monitor started");
        
    }
    
    void logMessage(){
    //Display error message in the console
    }

    @Override
    public void NotAlive(int id) throws RemoteException {
        System.out.println("Transaction Processor "+id+" has failed");
    }
}
