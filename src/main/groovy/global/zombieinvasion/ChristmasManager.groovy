package global.zombieinvasion

import akka.actor.ActorRef
import akka.actor.Props
import grails.util.Holders
import groovy.util.logging.Log

@Log
class ChristmasManager extends BaseActor {

    def akkaService = Holders.applicationContext.getBean("akkaService")

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

            } else if (message == "PLAY_CoolLights") {

                lightManager.tell("PLAY_CoolLights", akkaService.actorNoSender())

            } else if (message == "PLAY_WhiteChristmas") {

                lightManager.tell("PLAY_WhiteChristmas", akkaService.actorNoSender())

            } else if (message == "STOP_SHOW") {

                lightManager.tell("STOP_LIGHTS", akkaService.actorNoSender())

            }

        } else if (message instanceof ShowEvent){

            lightManager.tell(message, self)

        }

    }
}
