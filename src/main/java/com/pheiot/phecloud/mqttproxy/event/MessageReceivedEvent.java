/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.mqttproxy.event;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageReceivedEvent {
    private final JSONObject message;
}
