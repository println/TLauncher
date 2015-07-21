/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.model;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 *
 * @author Felipe Santos <live.proto at hotmail.com>
 */
public class ModuleManager implements Printer, Closeable {

    private boolean running;
    private final Object monitor = new Object();
    private final String JAVA_COMMAND = "java -jar ";
    private final String SERVER_STARTED_MESSAGE = "...Awaiting requests";
    private final ConsoleCommand command;
    private final String path;

    public ModuleManager(String path) {
        this.path = path;
        this.command = new ConsoleCommand(JAVA_COMMAND + path, this);
    }

    public void start() throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    command.execute();
                } catch (Exception ex) {
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.isDaemon();
        thread.start();
        running = true;
        synchronized (monitor) {
            while (running) {
                try {
                    this.monitor.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public ConnectionFactory getConfig() {
        Properties properties = readConfig(path);
        ConnectionFactory factory = new ConnectionFactory(properties);
        return factory;
    }

    @Override
    public void setInputStream(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "CP857"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (SERVER_STARTED_MESSAGE.equals(line)) {
                running = false;
                synchronized (monitor) {
                    monitor.notifyAll();
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        command.abort();
    }

    private Properties readConfig(String path){
        String name = "config.properties";
        File base;
        try {
            base = new File(path);
            File configFile = new File(base.getParent(), name);
            Properties properties;
            try (FileInputStream fileInput = new FileInputStream(configFile)) {
                properties = new Properties();
                properties.load(fileInput);
            }
            return properties;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
