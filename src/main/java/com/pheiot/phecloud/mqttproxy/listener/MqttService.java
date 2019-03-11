/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.mqttproxy.listener;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/*
 * MQTT服务
 * @author Peter Li
 * @date 2019/3/11
 */
public interface MqttService {

    /**
     * 连接MQTT服务器
     * @param clientId 客户端ID
     * @throws MqttException 异常
     * @return MqttClient
     *
     */
    MqttClient connect(String clientId) throws MqttException;

    /**
     * 订阅某个主题，qos默认为0
     * @param client 客户端
     * @param topic  主题
     * @throws MqttException MqttException
     */
    void subscribe(MqttClient client, String topic) throws MqttException;

    /**
     * 订阅某些主题，qos默认为0
     * @param client 客户端
     * @param topic  主题
     * @throws MqttException MqttException
     */
    void subscribe(MqttClient client, String[] topic) throws MqttException;

    /**
     * 订阅某个主题
     * @param client 客户端
     * @param topic  主题
     * @param qos  级别类型
     * @throws MqttException MqttException
     */
    void subscribe(MqttClient client, String topic, int qos) throws MqttException;

    /**
     * 订阅某些主题
     * @param client 客户端
     * @param topic  主题
     * @param qos  级别类型
     * @throws MqttException MqttException
     */
    void subscribe(MqttClient client, String[] topic, int[] qos) throws MqttException;

    /**
     * 发布主题消息
     * @param deviceSn 设备序列号
     * @param message 主题信息内容
     * @param qos 消息质量
     * @param topic 主题
     * @throws Exception 异常
     */
    void publishMessage(String deviceSn, String topic, String message, int qos) throws Exception;

    /**
     * 断开和MQTT服务的连接
     * @param client mqtt客户端
     * @throws MqttException 异常
     */
    void disconnect(MqttClient client) throws MqttException;

}
