/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pheiot.bamboo.common.utils.mapper.BeanMapper;
import com.pheiot.bamboo.common.utils.time.DateFormatUtil;
import com.pheiot.bamboo.common.utils.time.DateUtil;
import com.pheiot.phecloud.core.dao.DeviceDataDao;
import com.pheiot.phecloud.core.dto.DeviceDataDto;
import com.pheiot.phecloud.core.dto.DeviceDataQueryConditionDto;
import com.pheiot.phecloud.core.entity.DeviceData;
import com.pheiot.phecloud.core.service.DeviceDataService;
import com.pheiot.phecloud.utils.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Autowired
    private DeviceDataDao deviceDataDao;

    @Override
    public List<DeviceDataDto> findPeriodDataByProductAndDevice(DeviceDataQueryConditionDto condition) {

        if (condition == null
                || StringUtils.isBlank(condition.getProductKey())
                || StringUtils.isBlank(condition.getDevKey())
                || condition.getStartTime() == null
                || condition.getEndTime() == null) {
            throw new ApplicationException("参数错误。");
        }

        List<DeviceData> list = deviceDataDao.findDeviceData(condition);
        List<DeviceDataDto> response = Lists.newArrayList();

        for (DeviceData entity : list) {
            DeviceDataDto dto = BeanMapper.map(entity, DeviceDataDto.class);
            response.add(dto);
        }

        return response;
    }

    @Override
    public void addMetrics(Map<String, Object> data) {
        if (StringUtils.isBlank(data.get("product_key").toString())
                || StringUtils.isBlank(data.get("dev_key").toString())) {
            throw new ApplicationException("参数错误。");
        }

        String productKey = data.get("product_key").toString();
        String devKey = data.get("dev_key").toString();
        String ts = data.get("ts").toString();

        //构造当前上传时间对应的整点小时
        DateTimeFormatter format = DateTimeFormat.forPattern(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND);
        DateTime tsTime = DateTime.parse(ts, format);
        Date beginOfHour = DateUtil.beginOfHour(tsTime.toDate());

        //构造查询条件，用来定位数据文档
        DeviceDataQueryConditionDto condition = new DeviceDataQueryConditionDto();
        condition.setProductKey(productKey);
        condition.setDevKey(devKey);
        condition.setExactHoursTime(beginOfHour);
        condition.setMinuteOfHours(String.valueOf(tsTime.getMinuteOfHour()));

        //metrics 数据格式为 {key:value}
        //如：{ "tmp" : "25", "attr1" : "Hello" }
        Map metricsMap = (Map) data.get("metrics");

        //判断是否已经存在文档，存在则更新，不存在则创建
        if (deviceDataDao.isExistDocument(condition)) {
            deviceDataDao.addMetrics(condition, metricsMap);
        } else {
            deviceDataDao.addDeviceData(condition, metricsMap);
        }
    }
}
