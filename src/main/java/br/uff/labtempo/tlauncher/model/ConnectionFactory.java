/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.model;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.service.OmcpService;
import br.uff.labtempo.omcp.service.rabbitmq.RabbitService;
import java.util.Properties;

/**
 *
 * @author Felipe Santos <live.proto at hotmail.com>
 */
public class ConnectionFactory {

    private String ip;
    private String user;
    private String pass;

    public ConnectionFactory(Properties config) {
        this.ip = config.getProperty("rabbitmq.server.ip");
        this.user = config.getProperty("rabbitmq.user.name");
        this.pass = config.getProperty("rabbitmq.user.pass");
    }

    public OmcpClient getClient() {
        return new RabbitClient(ip, user, pass);
    }

    public OmcpService getService() {
        return new RabbitService(ip, user, pass);
    }
}
