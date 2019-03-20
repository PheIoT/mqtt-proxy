/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.dao;

import com.pheiot.phecloud.core.dto.DeviceDataQueryConditionDto;
import com.pheiot.phecloud.core.entity.DeviceData;

import java.util.List;
import java.util.Map;

public interface DeviceDataDao {

    boolean isExistDocument(DeviceDataQueryConditionDto condition);

    List<DeviceData> findDeviceData(DeviceDataQueryConditionDto condition);

    void addDeviceData(DeviceDataQueryConditionDto condition, Map<String, Object> addParams);

    void addMetrics(DeviceDataQueryConditionDto condition, Map<String, Object> addParams);

}
