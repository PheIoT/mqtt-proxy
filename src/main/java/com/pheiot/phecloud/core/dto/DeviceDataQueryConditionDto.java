/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceDataQueryConditionDto {

    private String productKey;
    private String devKey;

    //Datetime Format : "yyyy-MM-dd HH:mm:ss"
    private Date startTime;
    private Date endTime;
    private Date exactHoursTime;
    private String minuteOfHours;

}
