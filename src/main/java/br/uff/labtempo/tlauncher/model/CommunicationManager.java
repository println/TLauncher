/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.model;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.service.OmcpService;
import br.uff.labtempo.tlauncher.data.DataBase;

/**
 *
 * @author Felipe Santos <fralph at ic.uff.br>
 */
public class CommunicationManager {
    private ConnectionFactory factory;
    private String testName;
    private OmcpClient client;
    private OmcpService service;

    public CommunicationManager(ConnectionFactory factory, String testName) {
        this.factory = factory;
        this.testName = testName;
        this.client = factory.getClient();
        this.service = factory.getService();        
    }
    
    private void createLink(int count){
        String networkId = DataBase.NETWORK_ID;
        String collectorId = testName;
    }
    
    
}
