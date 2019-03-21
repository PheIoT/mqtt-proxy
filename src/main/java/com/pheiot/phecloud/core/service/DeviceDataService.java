/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.service;

import com.pheiot.phecloud.core.dto.DeviceDataDto;
import com.pheiot.phecloud.core.dto.DeviceDataQueryConditionDto;

import java.util.List;
import java.util.Map;

public interface DeviceDataService {

    List<DeviceDataDto> findPeriodDataByProductAndDevice(DeviceDataQueryConditionDto condition);

    /**
     * 增加传感器上传的数据。如果当前小时已存在记录，则更新，如果不存在，则新增一条。
     *
     * 必要字段：
     * productKey - 产品Key
     * deviceKey - 设备Key
     * exactHoursTime - 数据当前小时所对应时间
     */
    void addMetrics(Map<String, Object> data);
}
