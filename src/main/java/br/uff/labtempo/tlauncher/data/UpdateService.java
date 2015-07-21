/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.data;

import br.uff.labtempo.omcp.common.Request;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.omcp.service.EventHandler;
import br.uff.labtempo.omcp.service.OmcpService;

/**
 *
 * @author Felipe Santos <fralph at ic.uff.br>
 */
public abstract class UpdateService<T> implements AutoCloseable, EventHandler {

    private OmcpService service;
    private final UpdateListener<T> listener;
    private final Class<?> klass;
    private final String address;

    public UpdateService(OmcpService service, UpdateListener<T> listener, Class<?> klass, String address) {
        this.service = service;
        this.listener = listener;
        this.klass = klass;
        this.address = address;
    }

    public void start() {
        service.addReference(address);
        service.setHandler(this);
        service.start();
    }

    @Override
    public void close() throws Exception {
        service.close();
    }

    @Override
    public void process(Request request) {
        try {
            T object = request.getContent(klass);
            long unpackingTimestamp = System.currentTimeMillis();
            listener.updateReceived(object, unpackingTimestamp);
        } catch (BadRequestException ex) {
        }
    }

    public boolean isStarted() {
        return service.isStarted();
    }
}
