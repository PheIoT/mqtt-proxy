/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.mqttproxy.listener;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.Subscribe;
import com.pheiot.bamboo.common.utils.mapper.JsonMapper;
import com.pheiot.phecloud.core.service.DeviceDataService;
import com.pheiot.phecloud.mqttproxy.event.MessageReceivedEvent;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class MessageEventListener {

    public JSONObject message;

    @Autowired
    private DeviceDataService deviceDataService;

    @Subscribe
    public void listen(MessageReceivedEvent event) {
        message = event.getMessage();
        deviceDataService.addMetrics(JsonMapper.defaultMapper().fromJson(message.toString(), Map.class));
    }
}
