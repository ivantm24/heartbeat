/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeatimplementation;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ivantactukmercado
 */
public interface RmiServerIntf extends Remote{
    public int getId()throws RemoteException;
    public void pitAPat(int id)throws RemoteException;
}
