/*
 * Copyright 2015 Felipe Santos <fralph at ic.uff.br>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.uff.labtempo.tlauncher.data;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe Santos <fralph at ic.uff.br>
 */
public class VirtualSensorNetDataBuilder {

    private OmcpClient client;
    private long dataTypeId;
    private List<Long> linkIds;

    public VirtualSensorNetDataBuilder(OmcpClient client) {
        this.client = client;
        this.linkIds = new ArrayList<>();
    }

    public void populate(String collectorId, String sensorsId) {
        List<String> l = new ArrayList<>();
        l.add(sensorsId);
        populate(collectorId, l);
    }

    public void populate(String collectorId, List<String> sensorsIds) {
        if (dataTypeId == 0) {
            createDataType();
        }

        for (String sensorsId : sensorsIds) {
            LinkVsnTo lvt = generateLink(collectorId, sensorsId, dataTypeId);
            Response r = client.doPost(DataBase.RESOURCE_LINK, lvt);
            if (r.getStatusCode() == StatusCode.CREATED) {
                r = client.doGet(r.getLocation());
                lvt = r.getContent(LinkVsnTo.class);
                linkIds.add(lvt.getId());
            } else {
                throw new RuntimeException(r.getStatusCode().toString() + ":" + r.getErrorMessage());
            }
        }
    }

    private void createDataType() {
        DataTypeVsnTo dtvt = generateDataType();
        Response r = client.doPost(DataBase.RESOURCE_DATATYPE, dtvt);
        if (r.getStatusCode() == StatusCode.CREATED) {
            r = client.doGet(r.getLocation());
            dtvt = r.getContent(DataTypeVsnTo.class);
            dataTypeId = dtvt.getId();
        } else {
            throw new RuntimeException(r.getStatusCode().toString() + ":" + r.getErrorMessage());
        }
    }

    private static DataTypeVsnTo generateDataType() {
        DataTypeVsnTo dtvts = new DataTypeVsnTo(DataBase.DATA_NAME, DataBase.DATA_TYPE, DataBase.DATA_UNIT, DataBase.DATA_SYMBOL);
        return dtvts;
    }

    private static LinkVsnTo generateLink(String collectorId, String sensorId, long dataTypeId) {
        LinkVsnTo linkVsnTo = new LinkVsnTo(sensorId, collectorId, DataBase.NETWORK_ID);
        linkVsnTo.createField(DataBase.DATA_NAME, dataTypeId);
        return linkVsnTo;
    }
}
