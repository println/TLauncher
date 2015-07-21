/* 
 * Copyright 2015 Felipe Santos <fralph at ic.uff.br>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.uff.labtempo.tlauncher.testsuite;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.service.OmcpService;
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
public class LatencyTestSuite implements UpdateListener<VirtualSensorVsnTo> , TestSuite{

    private String testName;
    private String sensorId;
    private final Object monitor = new Object();
    private boolean waiting;
    private FilePrinter printer;
    private final int testLoop;

    public LatencyTestSuite() {
        this.testName = "latency";
        this.sensorId = "1";
        this.testLoop = 100;
    }

    @Override
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
            for (int i = 1; i <= testLoop; i++) {
                collectorDataBuilder.publishSample(sensorId, i);
                waiting = true;
                synchronized (monitor) {
                    while (waiting) {
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
