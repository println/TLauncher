/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Felipe Santos <fralph at ic.uff.br>
 */
public class FilePrinter implements AutoCloseable {

    private final FileWriter writer;
    public static String newline = System.getProperty("line.separator");

    public FilePrinter(String fileName, File folder, boolean isAppendable) throws IOException {
        this.writer = configWriter(fileName, folder, isAppendable);
    }

    public FilePrinter(String fileName, boolean isAppendable) throws IOException {
        this(fileName, new File("."), isAppendable);
    }

    public FilePrinter(String fileName) throws IOException {
        this(fileName, false);
    }

    private FileWriter configWriter(String fileName, File folder, boolean isAppendable) throws IOException {
        File file = new File(folder, fileName);
        FileWriter writer = new FileWriter(file, isAppendable);
        return writer;
    }

    public void println(Object x) throws IOException {
        println(x.toString());
    }

    public void print(Object x) throws IOException {
        print(x.toString());
    }

    public void println(long x) throws IOException {
        println(String.valueOf(x));
    }

    public void print(long x) throws IOException {
        print(String.valueOf(x));
    }

    public void println(String x) throws IOException {
        print(x);
        print(newline);
    }

    public void print(String x) throws IOException {
        writer.write(x);
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}
