/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pheiot.bamboo.common.utils.mapper.BeanMapper;
import com.pheiot.bamboo.common.utils.time.DateFormatUtil;
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

//        DateTimeFormatter format = DateTimeFormat.forPattern(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND);
//        Date start = DateTime.parse("2019-03-18 09:00:00", format).toDate();
//        Date end = DateTime.parse("2019-03-18 09:30:00", format).toDate();
//        Date start = DateTime.parse(condition.getStartTime(), format).toDate();
//        Date end = DateTime.parse(condition.getEndTime(), format).toDate();

        List<DeviceData> list = deviceDataDao.findDeviceData(condition);
        List<DeviceDataDto> response = Lists.newArrayList();

        for (DeviceData entity : list) {
            DeviceDataDto dto = BeanMapper.map(entity, DeviceDataDto.class);
            response.add(dto);
        }

        return response;
    }

    @Override
    public void addMetrics(String productKey, String deviceKey, Map<String, Object> addParams) {

    }
}
