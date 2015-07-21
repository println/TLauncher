/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Felipe Santos <live.proto at hotmail.com>
 */
public class ConsolePrinter implements Printer {

    private String charset;

    public ConsolePrinter() {
        this.charset = "CP857";
        //"CP857"
    }

    public ConsolePrinter(String charset) {
        this.charset = charset;
    }

    @Override
    public void setInputStream(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
