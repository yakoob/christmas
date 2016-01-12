package global.zombieinvasion

import grails.util.Holders
import groovy.util.logging.Log
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import grails.converters.JSON

enum Status {ON,BLINK,OFF,ALL_ON,ALL_OFF,ALL_BLINK,CRAWL,BACK_AND_FORTH,INNER_OUT,OUTER_IN,BANANAS
}

@Log
class LightManager extends BaseActor {

    def akkaService = Holders.applicationContext.getBean("akkaService")
    def httpClientService = Holders.applicationContext.getBean("httpClientService")
    def mqttClientService = Holders.applicationContext.getBean("mqttClientService")

    def showService = Holders.applicationContext.getBean("showService")

    private static final String lightControllerIp = "192.168.20.161"

    Status lightManagerStatus = Status.ON

    String currentSong

    long when
    long timersTotalWhen = 0l

    @Override
    void onReceive(Object message) throws Exception{

        if (message instanceof ShowEvent) {

            this.currentSong = message.song

            println "lightManager: $message"

            if (message.status == ShowEvent.Status.PLAY && message.song) {

                if (!isIdle()) {
                    println "can not run PLAY_JingleBells because a program is running... timersTotalWhen:$timersTotalWhen | when:$when... Program will be idle in ${(when-timersTotalWhen)/60} seconds"
                    return
                }

                reset()

                actionMusicBellsMusic("play", this.currentSong)

                showService.getInstrucations(message)?.each { scheduleLight(it.node, it.status, it.when) }

                scheduleLight("0", Status.ALL_ON, 3000l)

            } else if (message.status == ShowEvent.Status.STOP) {

                reset()
                this.lightManagerStatus = Status.OFF

            } else {

                println "unexpected event..."

            }

        } else if (message instanceof String){
            if (message == "ON") {
                println "turn lights on"
                reset()
                httpClientService.execute("http://$lightControllerIp", "arduino/relay/0/",Status.ALL_ON)
            }
        }

    }

    void reset(){
        this.lightManagerStatus = Status.ON
        timersTotalWhen = 0l
        when = 0l
        actionMusicBellsMusic("stop", this.currentSong)
        stopAllLights()
    }

    void stopAllLights(){
        httpClientService.execute("http://$lightControllerIp", "arduino/relay/0/",Status.ALL_OFF)
    }

    private boolean isIdle(){
        if ((when - timersTotalWhen) <= 0) {
            println "isIdle"
            return true
        } else {
            println "program running"
            return false
        }

    }

    def actionMusicBellsMusic(String action, String song){
        def res = [:]
        res.action = action
        res.song = song
        String payload = res as JSON
        mqttClientService.publish("audioPlayer", payload)
    }

    def cumulativeWhen(long x) {
        this.when = when + x
        return this.when
    }

    def scheduleLight(String node, Status status, long w){

        if (lightManagerStatus != Status.ON)
            return

        long cumulativeWhen = cumulativeWhen(w)

        akkaService.system.scheduler().scheduleOnce(Duration.create(cumulativeWhen, TimeUnit.MILLISECONDS),
            new Runnable() {
                @Override
                public void run() {

                    if (lightManagerStatus != Status.ON){
                        println "lightManagerStatus is OFF so I cannot execute: [node:$node | status:$status | when:$cumulativeWhen]"
                        return
                    }

                    timersTotalWhen = timersTotalWhen + w

                    println("node:$node | status:$status | when:$cumulativeWhen")
                    httpClientService.execute("http://$lightControllerIp", "arduino/relay/${node}/",status)
                }
            }, akkaService.system.dispatcher()
        )
    }

}
