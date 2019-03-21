/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.mqttproxy.api;

import com.pheiot.phecloud.mqttproxy.handler.MqttHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext
public class MqttTest {

    @Autowired
    private MqttHandler mqttService;

    @Test
    public void mqttTest() {
        try {
            mqttService.publishMessage("device-1", "product-1/device-1/topic", "Hello from PheIot.", 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
