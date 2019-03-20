/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.service.impl;

import com.google.common.collect.Maps;
import com.pheiot.bamboo.common.utils.time.DateFormatUtil;
import com.pheiot.bamboo.common.utils.time.DateUtil;
import com.pheiot.phecloud.core.dao.DeviceDataDao;
import com.pheiot.phecloud.core.dto.DeviceDataDto;
import com.pheiot.phecloud.core.dto.DeviceDataQueryConditionDto;
import com.pheiot.phecloud.core.entity.DeviceData;
import com.pheiot.phecloud.core.service.DeviceDataService;
import com.pheiot.phecloud.mqttproxy.MqttProxyApplication;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MqttProxyApplication.class)
@DirtiesContext
public class DeviceDataServiceTest {

    @Autowired
    private DeviceDataService deviceDataService;

    private DeviceDataQueryConditionDto condition;

    @Before
    public void setup() {
        condition = new DeviceDataQueryConditionDto();
        condition.setProductKey("123456789012345678901234567890");
        condition.setDevKey("123456789012");
    }

    @Test
    public void dataFindTest() {
        try {

            DateTimeFormatter format = DateTimeFormat.forPattern(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND);
            Date start = DateTime.parse("2019-03-18 09:00:00", format).toDate();
            Date end = DateTime.parse("2019-03-18 09:30:00", format).toDate();

            condition.setStartTime(start);
            condition.setEndTime(end);
            List<DeviceDataDto> list = deviceDataService.findPeriodDataByProductAndDevice(condition);
            System.out.println(list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
