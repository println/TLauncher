/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.labtempo.tlauncher.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Felipe Santos <live.proto at hotmail.com>
 */
public class PrintToFile {
    private String folderName;

    public PrintToFile(String folderName) throws IOException {
        this.folderName = folderName;
        FileWriter fileWriter = new FileWriter(folderName+File.separator+"name.txt");
    }
    
    public void restart(){}
    public void print(){}
    public void finish(){}
}
