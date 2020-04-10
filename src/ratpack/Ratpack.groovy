import java.util.stream.Stream

import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {
        get("playGw2") {
            def iterator = Stream.generate({ -> "Not today" }).iterator()
            render iterator.next()
        }
    }
}
