package global.zombieinvasion

import akka.actor.ActorRef
import akka.actor.Props
import grails.util.Holders
import groovy.util.logging.Log

@Log
class ChristmasManager extends BaseActor {

    def akkaService = Holders.applicationContext.getBean("akkaService")
    def httpClientService = Holders.applicationContext.getBean("httpClientService")
    def mqttClientService = Holders.applicationContext.getBean("mqttClientService")

    private ActorRef lightManager = null

    public ChristmasManager() {
        lightManager = context.system().actorOf(Props.create(LightManager.class), "global.zombieinvasion.LightManager")
    }

    @Override
    void onReceive(Object message) throws Exception {

        if (message instanceof String) {

            println "christmasManager onReceive: $message"

            if (message == "PLAY_JingleBells") {

                lightManager.tell("PLAY_JingleBells", self)

            } else if (message == "STOP_SHOW") {

                lightManager.tell("STOP_LIGHTS", akkaService.actorNoSender())

            }

        }

    }
}
