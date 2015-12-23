package global.zombieinvasion

class TestController {

    def akkaService

    def index() {
        render "test"
    }

    def jingle() {
        def message = new ShowEvent(ShowEvent.Status.PLAY, "dog_jingle_bells")
        akkaService.christmasManager.tell(message, akkaService.actorNoSender())
        render message
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
        def message = new ShowEvent(ShowEvent.Status.PLAY, "white_christmas")
        akkaService.christmasManager.tell(message, akkaService.actorNoSender())
        render message
    }

    def stop() {

        def message = new ShowEvent(ShowEvent.Status.STOP, null)
        akkaService.christmasManager.tell(message, akkaService.actorNoSender())
        render message

    }

}
