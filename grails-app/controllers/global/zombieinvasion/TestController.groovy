package global.zombieinvasion

class TestController {

    def akkaService

    def index() {

        akkaService.christmasManager.tell("PLAY_JingleBells", akkaService.actorNoSender())

        render "PLAY_JingleBells"

    }

    def stop() {

        akkaService.christmasManager.tell("STOP_SHOW", akkaService.actorNoSender())

        render "stop"
    }

}
