/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeatimplementation;

/**
 *
 * @author neloh
 */
public class FaultDetector {
    int checkingInterval = 15000; //in miliseconds
    long checkingTime;
    long expireTime;
    long lastUpdatedTime;
    boolean isAlive  = true;
    
    void checkAlive(){
        if(lastUpdatedTime < expireTime)
            isAlive = false;
        
        if(!isAlive){
            //Raise exception
        }
            
    }

    void pitAPat(){
        updateTime();
    }

    void updateTime(){
        this.lastUpdatedTime = System.currentTimeMillis() / 1000L;
        this.expireTime = lastUpdatedTime + checkingInterval;
    }

}