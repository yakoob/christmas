package global.zombieinvasion

import grails.util.Holders
import groovy.util.logging.Log
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import grails.converters.JSON

enum Status {ON,BLINK,OFF,ALL_ON,ALL_OFF,ALL_BLINK}

@Log
class LightManager extends BaseActor {

    def akkaService = Holders.applicationContext.getBean("akkaService")
    def httpClientService = Holders.applicationContext.getBean("httpClientService")
    def mqttClientService = Holders.applicationContext.getBean("mqttClientService")

    private static final String lightControllerIp = "192.168.20.1"

    Status lightManagerStatus = Status.ON

    String currentSong

    long when
    long timersTotalWhen = 0l

    @Override
    void onReceive(Object message) throws Exception{

        if (message instanceof String) {

            if (message == "PLAY_JingleBells"){

                println "LightManager: PLAY_JingleBells"

                if (!isIdle()) {
                    println "can not run PLAY_JingleBells because a program is running... timersTotalWhen:$timersTotalWhen | when:$when... Program will be idle in ${(when-timersTotalWhen)/60} seconds"
                    return
                }

                reset()

                currentSong = "dog_jingle_bells"

                actionMusicBellsMusic("play", this.currentSong)

                jingleBells().each { scheduleLight(it.node, it.status, it.when) }

                scheduleLight("0", Status.ALL_ON, 3000l)

            } else if (message == "STOP_LIGHTS") {

                reset()
                this.lightManagerStatus = Status.OFF

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
            lightManagerStatus = Status.ON

            return true
        } else {
            println "program running"
            lightManagerStatus = Status.OFF
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

    def jingleBells(){

        def instructions = []

        /*** Begin First Melody ***/
        instructions.add(["node":"9", "status":Status.BLINK, "when":4700])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"9", "status":Status.BLINK, "when":1000])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"9", "status":Status.ON, "when":900])
        instructions.add(["node":"3", "status":Status.ON, "when":300])
        instructions.add(["node":"4", "status":Status.ON, "when":300])
        instructions.add(["node":"5", "status":Status.ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":400])

        instructions.add(["node":"9", "status":Status.BLINK, "when":1200])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":600])

        instructions.add(["node":"9", "status":Status.BLINK, "when":300])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"9", "status":Status.ON, "when":900])
        instructions.add(["node":"3", "status":Status.ON, "when":300])
        instructions.add(["node":"4", "status":Status.ON, "when":300])
        instructions.add(["node":"5", "status":Status.ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":400])
        /*** End First Melody ***/


        /*** Begin Second Melody ***/
        instructions.add(["node":"9", "status":Status.BLINK, "when":1200])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"9", "status":Status.BLINK, "when":1000])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"9", "status":Status.ON, "when":900])
        instructions.add(["node":"3", "status":Status.ON, "when":300])
        instructions.add(["node":"4", "status":Status.ON, "when":300])
        instructions.add(["node":"5", "status":Status.ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":400])

        instructions.add(["node":"9", "status":Status.BLINK, "when":1200])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":600])

        instructions.add(["node":"9", "status":Status.BLINK, "when":300])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"9", "status":Status.ON, "when":900])
        instructions.add(["node":"3", "status":Status.ON, "when":300])
        instructions.add(["node":"4", "status":Status.ON, "when":300])
        instructions.add(["node":"5", "status":Status.ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":400])
        /*** END second melody ***/

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])


        4.times{
            def nodes = ["9","3","4","5","6","7"]
            Collections.shuffle(nodes)
            instructions.add(["node":"${nodes.first()}", "status":Status.BLINK, "when":400])
        }
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":100])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1100])


        4.times{
            def nodes = ["9","3","4","5","6","7"]
            Collections.shuffle(nodes)
            instructions.add(["node":"${nodes.first()}", "status":Status.BLINK, "when":400])
        }
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":150])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1100])



        11.times{
            def nodes = ["9","3","4","5","6","7"]
            Collections.shuffle(nodes)
            instructions.add(["node":"${nodes.first()}", "status":Status.BLINK, "when":400])
        }
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":250])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1100])



        4.times{
            def nodes = ["9","3","4","5","6","7"]
            Collections.shuffle(nodes)
            instructions.add(["node":"${nodes.first()}", "status":Status.BLINK, "when":400])
        }
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1100])


        4.times{
            def nodes = ["9","3","4","5","6","7"]
            Collections.shuffle(nodes)
            instructions.add(["node":"${nodes.first()}", "status":Status.BLINK, "when":400])
        }
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":200])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1100])


        11.times{
            def nodes = ["9","3","4","5","6","7"]
            Collections.shuffle(nodes)
            instructions.add(["node":"${nodes.first()}", "status":Status.BLINK, "when":400])
        }
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":200])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1200])


        /*** Begin First Melody ***/
        instructions.add(["node":"9", "status":Status.BLINK, "when":400])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"9", "status":Status.BLINK, "when":1000])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"9", "status":Status.ON, "when":900])
        instructions.add(["node":"3", "status":Status.ON, "when":300])
        instructions.add(["node":"4", "status":Status.ON, "when":300])
        instructions.add(["node":"5", "status":Status.ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":400])

        instructions.add(["node":"9", "status":Status.BLINK, "when":1200])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":600])

        instructions.add(["node":"9", "status":Status.BLINK, "when":300])
        instructions.add(["node":"3", "status":Status.BLINK, "when":300])
        instructions.add(["node":"9", "status":Status.BLINK, "when":300])

        instructions.add(["node":"9", "status":Status.ON, "when":900])
        instructions.add(["node":"3", "status":Status.ON, "when":300])
        instructions.add(["node":"4", "status":Status.ON, "when":300])
        instructions.add(["node":"5", "status":Status.ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":300])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":400])
        /*** End First Melody ***/


        3.times {
            /*** Begin Second Melody ***/
            instructions.add(["node":"9", "status":Status.BLINK, "when":1200])
            instructions.add(["node":"3", "status":Status.BLINK, "when":300])
            instructions.add(["node":"9", "status":Status.BLINK, "when":300])

            instructions.add(["node":"9", "status":Status.BLINK, "when":1000])
            instructions.add(["node":"3", "status":Status.BLINK, "when":300])
            instructions.add(["node":"9", "status":Status.BLINK, "when":300])

            instructions.add(["node":"9", "status":Status.ON, "when":900])
            instructions.add(["node":"3", "status":Status.ON, "when":300])
            instructions.add(["node":"4", "status":Status.ON, "when":300])
            instructions.add(["node":"5", "status":Status.ON, "when":300])
            instructions.add(["node":"0", "status":Status.ALL_ON, "when":300])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":400])

            instructions.add(["node":"9", "status":Status.BLINK, "when":1200])
            instructions.add(["node":"3", "status":Status.BLINK, "when":300])
            instructions.add(["node":"9", "status":Status.BLINK, "when":300])

            instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":600])

            instructions.add(["node":"9", "status":Status.BLINK, "when":300])
            instructions.add(["node":"3", "status":Status.BLINK, "when":300])
            instructions.add(["node":"9", "status":Status.BLINK, "when":300])

            instructions.add(["node":"9", "status":Status.ON, "when":900])
            instructions.add(["node":"3", "status":Status.ON, "when":300])
            instructions.add(["node":"4", "status":Status.ON, "when":300])
            instructions.add(["node":"5", "status":Status.ON, "when":300])
            instructions.add(["node":"0", "status":Status.ALL_ON, "when":300])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":400])
            /*** END second melody ***/
        }


        return instructions

    }



}