/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.labtempo.tlauncher.data;

import br.uff.labtempo.osiris.to.common.definitions.ValueType;

/**
 *
 * @author Felipe Santos <fralph at ic.uff.br>
 */
public class DataBase {

    public final static String NETWORK_ID = "test";//NETWORK_ID
    public final static String RESULT_FOLDER = "results";
    public final static String DATA_NAME = "message";
    public final static String DATA_UNIT = "identifier";
    public final static String DATA_SYMBOL = "ID";
    public final static ValueType DATA_TYPE = ValueType.NUMBER;
    public final static String RESOURCE_DATATYPE = "omcp://virtualsensornet/datatype/";
    public final static String RESOURCE_CONVERTER = "omcp://virtualsensornet/converter/";
    public final static String RESOURCE_LINK = "omcp://virtualsensornet/link/";
    public final static String RESOURCE_COMPOSITE = "omcp://virtualsensornet/composite/";
    public final static String RESOURCE_BLENDING = "omcp://virtualsensornet/blending/";
    public final static String RESOURCE_FUNCTION = "omcp://virtualsensornet/function/";
    public final static String RESOURCE_UPDATE_VSN = "omcp://update.messagegroup/virtualsensornet/#";
}
