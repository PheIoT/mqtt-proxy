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

    /**
     * 创建文档，并保存接收到的数据。
     * 必要字段：
     * productKey - 产品Key
     * deviceKey - 设备Key
     * exactHoursTime - 数据当前小时所对应时间
     * setMinuteOfHours - 当前时间的分钟数
     *
     * metrics 数据格式为 {key:value}
     * 如：{ "tmp" : "25", "attr1" : "Hello" }
     */
    void addDeviceData(DeviceDataQueryConditionDto condition, Map<String, Object> metrics);

    /**
     * 如果属性存在则更新接收到的数据，如果不存在则创建。
     * 必要字段：
     * productKey - 产品Key
     * deviceKey - 设备Key
     * exactHoursTime - 数据当前小时所对应时间
     * setMinuteOfHours - 当前时间的分钟数
     *
     * metrics 数据格式为 {key:value}
     * 如：{ "tmp" : "25", "attr1" : "Hello" }
     */
    void addMetrics(DeviceDataQueryConditionDto condition, Map<String, Object> metrics);

}
