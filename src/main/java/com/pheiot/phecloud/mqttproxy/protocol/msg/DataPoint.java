/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.mqttproxy.protocol.msg;

import lombok.Data;

@Data
public class DataPoint {
    public String name;
    public String metaType;
    public String keyValue;

}
