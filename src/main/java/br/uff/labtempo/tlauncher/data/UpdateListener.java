/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.data;


/**
 *
 * @author Felipe Santos <fralph at ic.uff.br>
 */
public interface UpdateListener<T>{
    public void updateReceived(T object, long unpackingTimestamp);
}
