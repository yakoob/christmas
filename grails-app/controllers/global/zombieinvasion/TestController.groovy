package global.zombieinvasion

class TestController {

    def akkaService

    def index() {

        render "test"

    }

    def cool(){

        akkaService.christmasManager.tell("PLAY_CoolLights", akkaService.actorNoSender())
        render "PLAY_CoolLights"

    }

    def jingle() {

        akkaService.christmasManager.tell("PLAY_JingleBells", akkaService.actorNoSender())
        render "PLAY_JingleBells"

    }

    def white() {

        akkaService.christmasManager.tell("PLAY_WhiteChristmas", akkaService.actorNoSender())
        render "PLAY_WhiteChristmas"

    }


    def stop() {

        akkaService.christmasManager.tell("STOP_SHOW", akkaService.actorNoSender())

        render "stop"
    }

}
