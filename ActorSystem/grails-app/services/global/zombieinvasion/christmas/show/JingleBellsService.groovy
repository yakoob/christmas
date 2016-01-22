package global.zombieinvasion.christmas.show

import global.zombieinvasion.Status

class JingleBellsService {

    static transactional = false

    def getInstructions() {

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



        10.times{
            def nodes = ["9","3","4","5","6","7"]
            Collections.shuffle(nodes)
            instructions.add(["node":"${nodes.first()}", "status":Status.BLINK, "when":350])
        }

        instructions.add(["node":"0", "status":Status.BACK_AND_FORTH, "when":1000])

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":500])
        instructions.add(["node":"0", "status":Status.BACK_AND_FORTH, "when":1100])



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
