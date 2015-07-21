/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.testsuite;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.service.OmcpService;
import br.uff.labtempo.osiris.to.virtualsensornet.ValueVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import br.uff.labtempo.tlauncher.data.CollectorDataBuilder;
import br.uff.labtempo.tlauncher.data.DataBase;
import br.uff.labtempo.tlauncher.data.UpdateListener;
import br.uff.labtempo.tlauncher.data.UpdateServiceWrapper;
import br.uff.labtempo.tlauncher.data.VirtualSensorNetDataBuilder;
import br.uff.labtempo.tlauncher.data.VirtualSensorNetUpdateService;
import br.uff.labtempo.tlauncher.data.VirtualSensorPrintFormat;
import br.uff.labtempo.tlauncher.model.ConnectionFactory;
import br.uff.labtempo.tlauncher.model.ConsoleCommand;
import br.uff.labtempo.tlauncher.model.ModuleManager;
import br.uff.labtempo.tlauncher.persistence.FileManager;
import br.uff.labtempo.tlauncher.persistence.FilePrinter;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Felipe Santos <live.proto at hotmail.com>
 */
public class LatencyTestSuite implements UpdateListener<VirtualSensorVsnTo> {

    private String testName;
    private String sensorId;
    private final Object monitor = new Object();
    private boolean waiting;
    private FilePrinter printer;

    public LatencyTestSuite() {
        this.testName = "latency";
        this.sensorId = "1";
    }

    public void start() {
        //folder and file
        FileManager fileManager = new FileManager();
        //commands
        ConsoleCommand command = new ConsoleCommand("sudo service rabbitmq-server restart");
        ModuleManager manager = new ModuleManager("target\\VirtualSensorNet\\VirtualSensorNet.jar");

        ConnectionFactory factory = manager.getConfig();
        OmcpClient client = factory.getClient();
        OmcpService service = factory.getService();

        VirtualSensorNetDataBuilder vsnDataBuilder = new VirtualSensorNetDataBuilder(client);
        CollectorDataBuilder collectorDataBuilder = new CollectorDataBuilder(client, testName);
        VirtualSensorNetUpdateService updateService = new VirtualSensorNetUpdateService(service, this);
        UpdateServiceWrapper wrapper = new UpdateServiceWrapper(updateService);

        try {
            //create folder
            File mainFolder = fileManager.createFolder(DataBase.RESULT_FOLDER);
            String folderName = fileManager.getTimestampedFileName(testName);
            File folder = fileManager.createFolder(mainFolder, folderName);
            printer = fileManager.getFilePrinter(testName + ".txt", folder, true);
            printer.println(VirtualSensorPrintFormat.getHeaders());
            //commands
            //command.execute();
            manager.start();
            System.out.println("Criando link");
            vsnDataBuilder.populate(testName, sensorId);
            System.out.println("Iniciando service");
            wrapper.start();
            System.out.println("Enviando dados");
            for (int i = 1; i <= 100; i++) {
                collectorDataBuilder.publishSample(sensorId, i);
                waiting = true;
                System.out.print(".");
                while (waiting) {
                    synchronized (monitor) {
                        monitor.wait();
                    }
                }
            }

        } catch (Exception ex) {

        } finally {
            try {
                wrapper.close();
            } catch (Exception e) {
            }
            try {
                printer.close();
            } catch (Exception e) {
            }

            try {
                manager.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void updateReceived(VirtualSensorVsnTo object, long unpackingTimestamp) {
        System.out.print(":");
        try {
            printer.println(new VirtualSensorPrintFormat(object, unpackingTimestamp));
        } catch (IOException ex) {
        }
        synchronized (monitor) {
            waiting = false;
            monitor.notify();
        }
    }
}
