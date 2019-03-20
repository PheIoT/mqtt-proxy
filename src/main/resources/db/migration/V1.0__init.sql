// mongodb 数据结构示例
// collection 命名 : ${product_key}-${device_key}
// document : 以每小时为一个document,
{
    "_id": ObjectId("5c8f60791147651bd37de027"),

    //采集所属的时间,按每小时划分
    "hours": ISODate("2019-03-18T01:00:00.000Z"),

    //设备key
    "devkey": 123456789012,

    // 采集传感器的具体属性
    "metrics": {
    // 属性名:temp, 内容 - { 发送分钟数:传感器值 }
        "temp": {
            "0": "16",
            "5": "18",
            "10": "20",
            "22": "55"
        },
        "stat": {
            "0": "A",
            "5": "B",
            "10": "C",
            "22": "OH"
        }
    }
}
{
    "_id": ObjectId("5c8f60791147651bd37de028"),
    "devkey": "123456789012",
    "hours": ISODate("2019-03-18T02:00:00.000Z"),
    "metrics": {
        "temp": {
            "22": "55"
        },
        "stat": {
            "22": "OH"
        }
    }
}

