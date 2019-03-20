/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.dao;

import com.google.common.collect.Maps;
import com.pheiot.bamboo.common.utils.time.DateFormatUtil;
import com.pheiot.bamboo.common.utils.time.DateUtil;
import com.pheiot.phecloud.core.dto.DeviceDataQueryConditionDto;
import com.pheiot.phecloud.core.entity.DeviceData;
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
public class DeviceDaoTest {

    @Autowired
    private DeviceDataDao deviceDataDao;

    private Map<String, Object> map;
    private DeviceDataQueryConditionDto condition;

    @Before
    public void setup() {
        condition = new DeviceDataQueryConditionDto();
        condition.setProductKey("123456789012345678901234567890");
        condition.setDevKey("123456789012");

        map = Maps.newHashMap();
        map.put("product_key", "123456789012345678901234567890");
        map.put("dev_key", "123456789012");
    }

    //    @Test
//    public void dataFindTest() {
//        try {
////            map.put("hours_start", "2019-03-18T00:00:00.000Z");
////            map.put("hours_end", "2019-03-18T01:59:00.000Z");
//            List<DeviceData> list = deviceDataDao.findDeviceDataByHours("2019-03-18T01:00:00.000Z");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
    @Test
    public void dataFindTest() {
        try {

            DateTimeFormatter format = DateTimeFormat.forPattern(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND);
            Date start = DateTime.parse("2019-03-18 09:00:00", format).toDate();
            Date end = DateTime.parse("2019-03-18 09:30:00", format).toDate();

//            map.put("hours_start", start);
//            map.put("hours_end", end);
            condition.setStartTime(start);
            condition.setEndTime(end);
            List<DeviceData> list = deviceDataDao.findDeviceData(condition);
            System.out.println(list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void isExistDocument() {
        DateTimeFormatter format = DateTimeFormat.forPattern(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND);

        DateTime now1 = DateTime.parse("2019-03-18 09:22:30", format);
        Date d1 = DateUtil.beginOfHour(now1.toDate());

//        map.put("hours", d1);
        condition.setExactHoursTime(d1);

        boolean iscollection = deviceDataDao.isExistDocument(condition);
        System.out.println(iscollection);
    }

    @Test
    public void addMetricsTest() {
        DateTimeFormatter format = DateTimeFormat.forPattern(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND);
        DateTime now1 = DateTime.parse("2019-03-18 09:27:30", format);
        Date d1 = DateUtil.beginOfHour(now1.toDate());
        int minute = now1.getMinuteOfHour();

//        map.put("hours", d1);
//        map.put("minute", minute);

        condition.setExactHoursTime(d1);
        condition.setMinuteOfHours(String.valueOf(minute));

        Map<String, Object> data = Maps.newHashMap();
        data.put("metrics.temp", "55");
        data.put("metrics.stat", "OH");

        deviceDataDao.addMetrics(condition, data);
    }

}
