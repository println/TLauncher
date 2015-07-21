/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.controller;

/**
 *
 * @author Felipe Santos <live.proto at hotmail.com>
 */
public class TestController {

    /**
     * Efficiency Test
     */
    public void latenceTest() {
        /*
         * restart rabbit(pronto)
         ------------------------
         * iniciar virtualsensornet(pronto)
         * criar link
         * criar pasta do teste
         * criar arquivo de teste
         *      {criar service e client}
         * repetir 100x{ 
         *      enviar dados
         *      gravar os tempos em arquivos
         * } 
         */
    }
    public void flowRateTest() {
        /*
         * restart rabbit
         * iniciar virtualsensornet
         * criar link
         * enviar e recuperar dados
         * gravar os tempos em arquivos
         * enviar dados em pontencia de 10, iniciando com 10
         * limite do envio até o tempo de 1 segundo
         */
    }

    /**
     * Effectiveness Test
     */
    public void duplicateDataTest() {
        /*
         * restart rabbit
         * start sensornet
         * start virtualsensornet
         * send dual data and recovery unique data
         * print tuple to file
         */
    }

    public void switchingCollectorsTest() {
    }

}
