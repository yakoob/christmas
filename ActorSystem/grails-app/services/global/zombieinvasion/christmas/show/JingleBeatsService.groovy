package global.zombieinvasion.christmas.show

import grails.transaction.Transactional

import global.zombieinvasion.Status

@Transactional
class JingleBeatsService {

    def getInstructions() {

        def instructions = []
        def nodes = ["9","3","4","5","6","7"]
        def randomNodes = nodes.clone()

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":350])

        6.times {
            instructions.add(["node":"0", "status":Status.BACK_AND_FORTH, "when":1000])
        }

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":6000])

        3.times {

            11.times {
                instructions.add(["node":"0", "status":Status.ALL_OFF, "when":50])
                instructions.add(["node":"0", "status":Status.OUTER_IN, "when":0])
                instructions.add(["node":"0", "status":Status.ALL_OFF, "when":50])
                instructions.add(["node":"0", "status":Status.INNER_OUT, "when":0])
            }

            14.times {
                instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":250])
                instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":250])
                instructions.add(["node":"0", "status":Status.OUTER_IN, "when":0])
            }

            10.times {
                instructions.add(["node":"0", "status":Status.BANANAS, "when":50])
            }

            5.times {
                instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":250])
            }

        }

        2.times {
            instructions.add(["node":"0", "status":Status.OUTER_IN, "when":50])
        }

        8.times {
            instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":250])
        }

        5.times {
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":50])
            instructions.add(["node":"0", "status":Status.OUTER_IN, "when":0])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":50])
            instructions.add(["node":"0", "status":Status.INNER_OUT, "when":0])
        }

        5.times {
            instructions.add(["node":"0", "status":Status.BANANAS, "when":50])
        }

        10.times {
            Collections.shuffle(randomNodes)
            instructions.add(["node":"${randomNodes.first()}", "status":Status.BLINK, "when":350])
            instructions.add(["node":"0", "status":Status.ALL_ON, "when":350])
        }

        11.times {
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":50])
            instructions.add(["node":"0", "status":Status.OUTER_IN, "when":0])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":50])
            instructions.add(["node":"0", "status":Status.INNER_OUT, "when":0])
        }

        3.times {
            instructions.add(["node":"0", "status":Status.ALL_ON, "when":4000])
            instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":350])
            instructions.add(["node":"0", "status":Status.ALL_ON, "when":200])
            instructions.add(["node":"0", "status":Status.ALL_OFF, "when":200])
        }

        2.times {
            4.times {
                instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":250])
                instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":250])
                instructions.add(["node":"0", "status":Status.OUTER_IN, "when":0])
            }

        }

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":100])
        instructions.add(["node":"0", "status":Status.ALL_ON, "when":4000])

        5.times {
            instructions.add(["node":"0", "status":Status.ALL_ON, "when":100])
            instructions.add(["node":"0", "status":Status.ALL_ON, "when":3000])

            5.times {
                instructions.add(["node":"0", "status":Status.ALL_BLINK, "when":4000])
            }
        }

        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":100])
        instructions.add(["node":"0", "status":Status.ALL_OFF, "when":3000])

        18.times {
            instructions.add(["node":"0", "status":Status.BANANAS, "when":500])
        }

        instructions.add(["node":"0", "status":Status.ALL_ON, "when":100])

        return instructions

    }


}
