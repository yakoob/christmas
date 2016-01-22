package global.zombieinvasion

import groovy.transform.TupleConstructor

@TupleConstructor
class ShowEvent {

    enum Status {PLAY, STOP}

    Status status

    String song

    @Override
    public String toString(){
        return "status: $status | song: $song"
    }

}
