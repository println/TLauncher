/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.data;

import br.uff.labtempo.osiris.to.virtualsensornet.ValueVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;

/**
 *
 * @author Felipe Santos <fralph at ic.uff.br>
 */
public class VirtualSensorPrintFormat {

    long unpackingTimestamp;
    VirtualSensorVsnTo virtualSensorVsnTo;

    public VirtualSensorPrintFormat(VirtualSensorVsnTo virtualSensorVsnTo, long unpackingTimestamp) {
        this.unpackingTimestamp = unpackingTimestamp;
        this.virtualSensorVsnTo = virtualSensorVsnTo;
    }

    @Override
    public String toString() {
        ValueVsnTo valueVsnTo = virtualSensorVsnTo.getValuesTo().get(0);

        String id = valueVsnTo.getValue();
        long t1 = virtualSensorVsnTo.getCreationTimestampInMillis();
        long t2 = virtualSensorVsnTo.getStorageTimestampInMillis();
        long t3 = unpackingTimestamp;

        String tuple = id + "\t" + t1 + "\t" + t2 + "\t" + t3 + "\t" + (t2-t1) + "\t" + (t3-t1);
        
        return tuple;
    }
    
    public static String  getHeaders(){
        String header = "ID\tSent(ms)\tStored(ms)\tReceived(ms)\tStoreLatency(ms)\tEndToEndLatency(ms)";
        return header;        
    }

}
