/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.mqttproxy.protocol.msg;

import lombok.Data;

import java.util.List;

@Data
public class Message {
    private String productKey;
    private String deviceKey;
    private String token;
    private List<DataPoint> dataPoint;

}
