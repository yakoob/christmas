package global.zombieinvasion

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.cluster.Cluster
import groovy.util.logging.Log
import javax.annotation.PreDestroy

@Log
class AkkaService {

    static transactional = false

    private static ActorSystem system
    private static final ActorRef ACTOR_NO_SENDER = ActorRef.noSender()

    ActorRef christmasManager

    void init() {
        system = ActorSystem.create("christmas")
        actorSetup()
    }

    ActorSystem getSystem() {
        return system
    }

    @PreDestroy
    void destroy() {
        system?.shutdown()
        system = null
        log.error("destroying Akka ActorSystem: done.")
    }

    void actorSetup() {
        christmasManager = actorOf(ChristmasManager, "ChristmasManager")
    }

    ActorRef actorNoSender() {
        return ACTOR_NO_SENDER
    }

    Props props(Class clazz) {
        assert clazz != null
        Props props = Props.create(clazz)
        return props
    }

    ActorRef actorOf(Props props) {
        assert props != null
        assert system != null

        ActorRef actor = system.actorOf(props)
        return actor
    }

    ActorRef actorOf(Props props, String name) {
        assert props != null
        assert name != null

        assert system != null

        ActorRef actor = system.actorOf(props, name)
        return actor
    }

    ActorRef actorOf(Class clazz) {
        assert clazz != null

        Props props = props(clazz)
        assert props != null
        assert system != null

        ActorRef actor = system.actorOf(props)
        return actor
    }

    ActorRef actorOf(Class clazz, String name) {
        assert clazz != null
        assert name != null

        Props props = props(clazz)
        assert props != null
        assert system != null

        ActorRef actor = system.actorOf(props, name)
        return actor
    }

}
