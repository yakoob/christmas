class BootStrap {

    def akkaService
    def mqttClientService

    def init = { servletContext ->

        mqttClientService.init()
        akkaService.init()

    }

    def destroy = {
    }
}
