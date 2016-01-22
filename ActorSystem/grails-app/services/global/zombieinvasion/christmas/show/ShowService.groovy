package global.zombieinvasion.christmas.show

import global.zombieinvasion.ShowEvent

class ShowService {

    static transactional = false

    def jingleBellsService
    def jingleBeatsService
    def sugarPlumService
    def whiteChristmasService

    public List getInstrucations(ShowEvent event) {

        if (event.song == "dog_jingle_bells")

            return jingleBellsService.instructions

        else if (event.song == "jingle_beats")

            return jingleBeatsService.instructions

        else if (event.song == "sugar_plum_fairy"){

            return sugarPlumService.instructions

        } else if (event.song =="white_christmas"){

            return whiteChristmasService.instructions

        }

        return null
    }


}
