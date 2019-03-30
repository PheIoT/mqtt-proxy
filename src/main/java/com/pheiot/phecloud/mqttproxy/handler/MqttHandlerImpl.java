/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.mqttproxy.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.EventBus;
import com.pheiot.phecloud.mqttproxy.event.MessageReceivedEvent;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class MqttHandlerImpl implements MqttHandler {
    private static Logger log = LoggerFactory.getLogger("mqtt");

    @Autowired
    private EventBus eventBus;

    @Value("${mqtt.host}")
    private String host;
    @Value("${mqtt.user}")
    private String user;
    @Value("${mqtt.password}")
    private String password;

    public static String TOPIC_MQTT = "$SYS/brokers";

    @Override
    public MqttClient connect(String clientId) throws MqttException {
        MqttClient client = new MqttClient(host, clientId, new MemoryPersistence());
//        MqttClient client = new MqttClient(broker, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置连接的用户名
        options.setUserName(user);
        // 设置超时时间
        options.setConnectionTimeout(100);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(0);
        // 设置连接的密码
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(true);
        // 设置回调
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

                log.debug("连接丢失" + cause);
                try {
                    client.reconnect();
                } catch (MqttException e) {
                    log.error("重连失败");
                    e.printStackTrace();
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                try {
                    // subscribe后得到的消息会执行到这里面
                    String msgPayload = new String(message.getPayload()).trim();

                    //获取所有报文
                    JSONObject jsonPayload = JSONObject.parseObject(msgPayload.trim());
                    if (topic.startsWith(TOPIC_MQTT)) {
                        //TODO 处理设备连接消息
                        log.debug("设备上线：{}", jsonPayload);
                    } else {
                        //TODO 权限校验和自动注册
                        
                        //设备实际消息,触发消息处理事件
                        eventBus.post(new MessageReceivedEvent(jsonPayload));
                    }

                    log.debug("接收消息 topic: {}", topic);
                    log.debug("接收消息 payload: {}", jsonPayload);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    log.error("解析消息内容失败" + ex.getMessage());
                    ex.printStackTrace();
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                log.debug("deliveryComplete---------" + token.isComplete());
            }
        });

        if (!client.isConnected()) {
            client.connect(options);
        }
        return client;
    }

    @Override
    public void subscribe(MqttClient client, String topic) throws MqttException {
        try {
            subscribe(client, topic, 2);
        } catch (MqttException e) {
            log.error("订阅" + topic + "失败：", e);
            client.subscribe(topic, 2);
        }
    }

    @Override
    public void subscribe(MqttClient client, String[] topics) throws MqttException {
        for (String topic : topics) {
            subscribe(client, topic, 2);
        }
    }

    @Override
    public void subscribe(MqttClient client, String topic, int qos) throws MqttException {
        client.subscribe(topic, qos);
    }

    @Override
    public void subscribe(MqttClient client, String[] topics, int[] qos) {
        try {
            client.subscribe(topics, qos);
        } catch (MqttException e) {
            log.error("订阅主题" + "失败：", e);
        }
    }

    @Override
    public void publishMessage(String deviceSn, String topic, String message, int qos) throws Exception {
        MqttClient client = connect("mqtt_test" + UUID.randomUUID());
        try {
            log.info("发送的消息内容：" + message);
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(message.getBytes());
            mqttMessage.setQos(qos);
            mqttMessage.setRetained(false);
            client.publish(topic, mqttMessage);
            client.disconnect();
        } catch (MqttException e) {
            log.error(e.getMessage());
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public void disconnect(MqttClient client) throws MqttException {
        client.disconnect();
    }
}
