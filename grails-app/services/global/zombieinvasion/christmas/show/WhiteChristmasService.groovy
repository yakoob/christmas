package global.zombieinvasion.christmas.show

import global.zombieinvasion.Status

class WhiteChristmasService {

    static transactional = false

    def getInstructions() {

        def instructions = []
        def nodes = ["9","3","4","5","6","7"]

        2.times {

            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":350])

            6.times {
                instructions.add(["node":"0", "status":Status.BACK_AND_FORTH, "when":1000])
            }

            instructions.add(["node":"0", "status":Status.ALL_ON, "when":4000])

            4.times {
                instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":350])
                instructions.add(["node":"0", "status":Status.ALL_OFF, "when":100])
            }

            4.times {
                nodes.each { instructions.add(["node":"$it", "status":Status.ON, "when":350]) }
                nodes.reverse().each { instructions.add(["node":"$it", "status":Status.OFF, "when":350]) }
            }

            instructions.add(["node":"0", "status":Status.ALL_ON, "when":2000])

            3.times {
                instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":350])
                instructions.add(["node":"0", "status":Status.ALL_OFF, "when":100])
            }

            3.times{
                2.times {
                    instructions.add(["node":"0", "status":Status.BACK_AND_FORTH, "when":1000])
                }
                instructions.add(["node":"0", "status":Status.ALL_ON, "when":4000])
            }

        }

        return instructions

    }

}
