/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.core.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor()
public class KeyValue {

    private String key;
    private Object value;
}
