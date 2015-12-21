package global.zombieinvasion

import grails.artefact.Service
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class MqttClientService implements MqttCallback {

    public MqttClient mqttClient

    def akkaService

    void init() {
        mqttClient = new MqttClient("tcp://192.168.20.114:1883", "christmasAppOffice", mqttPersistence)
        mqttClient.connect();
        mqttClient.setCallback(this);
        mqttClient.subscribe("#") // subscript to all topics
        log.info "mqtt client connected"
    }

    private MemoryPersistence getMqttPersistence(){
        return new MemoryPersistence()
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error "connectionLost: ${cause.message} ${cause.stackTrace}"
        sleep(5000)
        mqttClient = null
        init()
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {

            /*
            Event event = messageSerializerService.serialize(topic, message?.toString())
            if (event)
                akkaService.getHalloweenManager().tell(event, akkaService.actorNoSender())
              */
            println "mqtt messageArrived: ${message.toString()}"
        } catch (e) {
            log.error e.stackTrace
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // println "mqtt deliveryComplete: ${token.toString()}"
    }


    def publish(String topic, String payload){
        MqttMessage message=new MqttMessage()
        message.setPayload(payload.bytes)
        mqttClient.publish(topic, message)
    }

}
