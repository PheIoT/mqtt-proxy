/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.mqttproxy.listener;

import com.pheiot.phecloud.mqttproxy.utils.ApplicationException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.UUID;

/**
 * ClassName ConnectMqttServerTask
 * @author Peter Li
 * @date 2019/3/11
 * Description  在后台服务启动时，监听MQTT服务端口
 */
@WebListener
public class MqttContextListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MqttService mqttListener;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        long startTime = System.currentTimeMillis();
        logger.info("开始执行启动任务,{}"+startTime);
        //业务处理
        try {
            String clientId = "SystemCheck"+UUID.randomUUID();
            String topic = "$SYS/brokers/+/clients/#";
            MqttClient clientCheck = mqttListener.connect(clientId);
            if(clientCheck.isConnected()){
                mqttListener.subscribe(clientCheck,topic);
            }
            MqttClient client = mqttListener.connect("MQTT-PROXY-"+UUID.randomUUID());
            client.subscribe("+/+/update");
            client.subscribe("+/+/error");
        }catch (ApplicationException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        logger.info("执行启动任务结束,共花费时间{}"+(endTime-startTime));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("在后台服务重启时，将之前的设备从表中拉取出来进行MQTT服务的重连"+sce);
    }
}
