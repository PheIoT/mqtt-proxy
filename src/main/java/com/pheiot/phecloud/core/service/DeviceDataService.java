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

    void addMetrics(String productKey, String deviceKey, Map<String, Object> addParams);
}
