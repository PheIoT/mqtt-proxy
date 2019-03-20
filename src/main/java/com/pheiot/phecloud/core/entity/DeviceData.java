/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.entity;

import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class DeviceData implements Serializable {
    private String id;
    private String devkey;
    private Date hours;
    private Map<String, Map<String, Object>> metrics = Maps.newHashMap();
}