package global.zombieinvasion

import grails.transaction.Transactional
import groovyx.net.http.AsyncHTTPBuilder
import reactor.spring.context.annotation.Consumer
import reactor.spring.context.annotation.Selector
import reactor.spring.context.annotation.SelectorType
import static groovyx.net.http.ContentType.HTML

@Transactional
@Consumer
class HttpClientService {

    def execute(String url, String path, Status status) {

        if (status == Status.ON) {

            path = path + 1

        } else if (status == Status.BLINK) {

            path = path + 2

        } else if (status == Status.OFF) {

            path = path + 0

        } else if (status == Status.ALL_OFF){

            path = path + 3
            println("http tell lights: ALL_OFF")

        } else if (status == Status.ALL_ON){

            path = path + 4
            println("http tell lights: ALL_ON")

        } else if (status == Status.ALL_BLINK){

            path = path + 5
            println("http tell lights: ALL_BLINK")

        }

        def http = new AsyncHTTPBuilder(
                poolSize : 1,
                uri : url,
                contentType : HTML )

        http.get(path:'/'+path)

        http = null



    }


    @Selector(value = /init.d(.+)/, type = SelectorType.REGEX)
    def foo(String arg){
        println "hello: $arg"
    }
}
