# mqtt-proxy

服务名称：mqtt-proxy
服务启动端口：8093

监听路径：
* +/+/update
* +/+/error
* $SYS/brokers/+/clients/#  获得emq 系统默认监听路径，判断client是否在线/离线
