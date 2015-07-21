/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.data;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe Santos <fralph at ic.uff.br>
 */
public class UpdateServiceWrapper implements AutoCloseable {

    private UpdateService service;
    private final Object monitor = new Object();
    private boolean waiting;

    public UpdateServiceWrapper(UpdateService service) {
        this.service = service;
    }

    public void start() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                waiting = false;
                synchronized (monitor) {
                    monitor.notify();
                }
                service.start();
            }
        };
        Thread t = new Thread(runnable);
        t.isDaemon();
        t.start();
        try {            
            synchronized (monitor) {
                while (waiting) {
                    monitor.wait();
                }
            }
            
            while (!service.isStarted()) {
                Thread.sleep(600);
            }
        } catch (InterruptedException ex) {
        }
    }

    @Override
    public void close() throws Exception {
        service.close();
    }
}
