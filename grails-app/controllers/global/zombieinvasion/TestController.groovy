package global.zombieinvasion

class TestController {

    def akkaService

    def index() {

        render "test"

    }

    def jingle() {

        akkaService.christmasManager.tell("PLAY_JingleBells", akkaService.actorNoSender())
        render "PLAY_JingleBells"

    }

    def jingleBeats() {
        def message = new ShowEvent(ShowEvent.Status.PLAY, "jingle_beats")
        akkaService.christmasManager.tell(message, akkaService.actorNoSender())
        render message
    }

    def sugarPlum() {
        def message = new ShowEvent(ShowEvent.Status.PLAY, "sugar_plum_fairy")
        akkaService.christmasManager.tell(message, akkaService.actorNoSender())
        render message
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
