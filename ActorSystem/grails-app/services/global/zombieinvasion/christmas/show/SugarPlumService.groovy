package global.zombieinvasion.christmas.show

import global.zombieinvasion.Status

class SugarPlumService {

    static transactional = false

    def getInstructions() {

        def instructions = []
        def nodes = [9,3,4,5,6,7,0]
        def randomNodes = [9,3,4,5,6,7,9]

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":0])
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":100])

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":1000])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":1000])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":1000])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":1000])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":1000])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":1000])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":1000])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])

        2.times {
            instructions.add(["node":"0", "status":Status.OUTER_IN, "when":100])

            instructions.add(["node":"0", "status":Status.ALL_ON, "when":500])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":500])

            instructions.add(["node":"0", "status":Status.INNER_OUT, "when":100])

            instructions.add(["node":"0", "status":Status.ALL_ON, "when":1000])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])

            instructions.add(["node":"0", "status":Status.ALL_ON, "when":1000])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])
        }

        instructions.add(["node":"0", "status":Status.OUTER_IN, "when":100])

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":500])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":500])

        instructions.add(["node":"0", "status":Status.INNER_OUT, "when":100])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":2000])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1200])


        4.times {
            nodes.each {

                if (it)
                    instructions.add(["node":"$it", "status":Status.ON, "when":1000])
                else
                    instructions.add(["node":"0", "status":Status.ALL_OFF, "when":0])

            }
        }

        Collections.shuffle(randomNodes)

        randomNodes.each {

            if (it)
                instructions.add(["node":"$it", "status":Status.ON, "when":1000])
            else
                instructions.add(["node":"0", "status":Status.ALL_OFF, "when":0])

        }

        9.times {

            instructions.add(["node":"0", "status":Status.OUTER_IN, "when":1000])
            instructions.add(["node":"0", "status":Status.INNER_OUT, "when":1000])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":1000])
            instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":250])

        }

        20.times {
            instructions.add(["node":"0", "status":Status.ALL_ON, "when":200])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":800])
        }

        10.times {
            instructions.add(["node":"0", "status":Status.ALL_ON, "when":500])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":500])
        }

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":0])


        instructions.add(["node":"9", "status":Status.ON, "when":2000])
        instructions.add(["node":"3", "status":Status.ON, "when":250])
        instructions.add(["node":"3", "status":Status.ON, "when":250])
        instructions.add(["node":"4", "status":Status.ON, "when":500])
        instructions.add(["node":"5", "status":Status.ON, "when":500])
        instructions.add(["node":"6", "status":Status.ON, "when":500])

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":0])

        instructions.add(["node":"7", "status":Status.ON, "when":500])
        instructions.add(["node":"9", "status":Status.ON, "when":250])
        instructions.add(["node":"3", "status":Status.ON, "when":250])

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":0])

        instructions.add(["node":"4", "status":Status.ON, "when":500])
        instructions.add(["node":"5", "status":Status.ON, "when":250])
        instructions.add(["node":"6", "status":Status.ON, "when":250])

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":0])

        instructions.add(["node":"7", "status":Status.ON, "when":500])
        instructions.add(["node":"4", "status":Status.ON, "when":250])
        instructions.add(["node":"5", "status":Status.ON, "when":250])

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":0])


        instructions.add(["node":"4", "status":Status.ON, "when":200])
        instructions.add(["node":"5", "status":Status.ON, "when":200])
        instructions.add(["node":"6", "status":Status.ON, "when":200])
        instructions.add(["node":"7", "status":Status.ON, "when":200])
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":0])

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":0])

        instructions.add(["node":"7", "status":Status.CRAWL, "when":0])

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":0])

        return instructions

    }



}
