# mqtt-proxy

##监听服务

服务名称：mqtt-proxy
服务启动端口：8093

监听路径：
* +/+/update
* +/+/error
* $SYS/brokers/+/clients/#  获得emq 系统默认监听路径，判断client是否在线/离线


## 数据存储

Mongodb 创建用户：

use admin
db.createUser({
    user:"root",
    pwd:"root",
    roles:[{
        role:"root",
        db:"admin"
    }]
})
db.auth("root", "root")

use shadow
db.createUser({
    user:"shadow",
    pwd:"shadow",
    roles:[{
        role:"dbAdmin",
        db:"shadow"
    },{
        role:"readWrite",
        db:"shadow"
    }]
})
db.auth("shadow", "shadow")