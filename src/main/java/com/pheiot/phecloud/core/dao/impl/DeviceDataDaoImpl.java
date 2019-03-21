/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.dao.impl;


import com.google.common.collect.Maps;
import com.pheiot.phecloud.core.dao.DeviceDataDao;
import com.pheiot.phecloud.core.dto.DeviceDataQueryConditionDto;
import com.pheiot.phecloud.core.entity.DeviceData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DeviceDataDaoImpl implements DeviceDataDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean isExistDocument(DeviceDataQueryConditionDto condition) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        criteria.and("hours").is(condition.getExactHoursTime());

        query.addCriteria(criteria);
        query.fields().include("hours");

        DeviceData data = mongoTemplate.findOne(query, DeviceData.class, getProductCollection(condition));
        return data != null;
    }

    @Override
    public List<DeviceData> findDeviceData(DeviceDataQueryConditionDto condition) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.where("devkey").is(condition.getDevKey());

        criteria.and("hours")
                .gte(condition.getStartTime())
                .lte(condition.getEndTime());

        query.addCriteria(criteria);

        return mongoTemplate.find(query, DeviceData.class, getProductCollection(condition));
    }

    @Override
    public void addDeviceData(DeviceDataQueryConditionDto condition, Map<String, Object> data) {

        //提取需要保存的传感器数据
        Map<String, Object> metricsMap = Maps.newHashMap();
        String minutePre = String.valueOf(condition.getMinuteOfHours());
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> metricsItemMap = Maps.newHashMap();
            metricsItemMap.put(minutePre, entry.getValue());
            metricsMap.put(key, metricsItemMap);
        }
        //构造保存数据
        Map<String, Object> map = Maps.newHashMap();
        map.put("devkey", condition.getDevKey());
        map.put("hours", condition.getExactHoursTime());
        map.put("metrics", metricsMap);

        mongoTemplate.save(map, getProductCollection(condition));
    }

    @Override
    public void addMetrics(DeviceDataQueryConditionDto condition, Map<String, Object> addParams) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        criteria.and("devkey").is(condition.getDevKey());
        criteria.and("hours").is(condition.getExactHoursTime());

        Update update = new Update();
        String minutePre = "." + condition.getMinuteOfHours();

        for (Map.Entry<String, Object> entry : addParams.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.isNotBlank(key)) {
                update.set("metrics." + key + minutePre, entry.getValue());
            }
        }

        mongoTemplate.updateFirst(query, update, DeviceData.class, getProductCollection(condition));
    }


    private String getProductCollection(DeviceDataQueryConditionDto condition) {
        return condition.getProductKey() + "-" + condition.getDevKey();
    }

}
