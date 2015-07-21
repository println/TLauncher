/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.model;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Felipe Santos <live.proto at hotmail.com>
 */
public interface Printer {

    void setInputStream(InputStream is) throws IOException;
}
