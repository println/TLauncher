/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.persistence;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Felipe Santos <fralph at ic.uff.br>
 */
public class FileManager {

    public File getFile(String address) {
        File file = new File(address);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public File createFolder(String address) {
        File root = new File(".");
        File folder = createFolder(root, address);
        return folder;
    }

    public File createFolder(File file, String address) {
        File folder = new File(file, address);
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                return null;
            }
        }
        return folder;
    }

    public FilePrinter getFilePrinter(String fileName, File folder, boolean isAppendable) throws IOException {
        FilePrinter printer = new FilePrinter(fileName, folder, isAppendable);
        return printer;
    }

    public String getTimestampedFileName(String prefix) {
        String fileName = new SimpleDateFormat("'" + prefix + "_'yyyyMMdd-HHmm").format(new Date());
        return fileName;
    }
}
