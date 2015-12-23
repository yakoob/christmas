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

        if (message instanceof ShowEvent){
            lightManager.tell(message, self)
        }

    }

}
