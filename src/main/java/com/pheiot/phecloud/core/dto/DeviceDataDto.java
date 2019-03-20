/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.dto;

import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class DeviceDataDto implements Serializable {
    private String id;
    private String devkey;
    private String productkey;
    private Date hours;
    private Map<String,  Map<String, Object>> metrics = Maps.newHashMap();
}