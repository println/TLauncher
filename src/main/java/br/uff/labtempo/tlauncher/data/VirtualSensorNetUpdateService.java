/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.data;

import br.uff.labtempo.omcp.service.OmcpService;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;

/**
 *
 * @author Felipe Santos <fralph at ic.uff.br>
 */
public class VirtualSensorNetUpdateService extends UpdateService<VirtualSensorVsnTo>{
    public VirtualSensorNetUpdateService(OmcpService service, UpdateListener<VirtualSensorVsnTo> listener) {
        super(service, listener, VirtualSensorVsnTo.class, "omcp://update.messagegroup/#");
    }
}
