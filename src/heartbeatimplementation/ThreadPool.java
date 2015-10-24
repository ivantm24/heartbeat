/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeatimplementation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author ivantactukmercado
 */
public class ThreadPool {
    private final Thread[] workerThreads;
    private volatile boolean shutdown;
    private final BlockingQueue<Runnable> workerQueue;
    
    
    public ThreadPool(int N){
        this.workerThreads=new Thread[N];
        this.workerQueue=new LinkedBlockingQueue<>();
        for (int i = 0; i < N; i++) {
            this.workerThreads[i]=new Worker("Thread "+i);
        }
    }
    
    private class Worker extends Thread{
        
        public Worker(String name){
            super(name);         
        }
        
        public void run(){
            while(!shutdown){
                try{
                    Runnable r=workerQueue.take();
                    r.run();
                }catch(InterruptedException e){
                    
                }
            }
        }
    }
    
}
