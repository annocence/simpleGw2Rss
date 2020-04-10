import de.ml.gw2.Gw2Module
import de.ml.gw2.PlayGw2Evaluator
import ratpack.registry.Registry

import static ratpack.groovy.Groovy.ratpack


ratpack {
    bindings {
        module Gw2Module
    }
    handlers {
        get("playGw2") { PlayGw2Evaluator playGw2Evaluator ->
            render playGw2Evaluator.evaluatePlayGw2()
        }

        get("set/:dailyanswer") { PlayGw2Evaluator playGw2Evaluator ->
            def newValue = context.getPathTokens().get("dailyanswer")
            playGw2Evaluator.setDailyMessage(newValue)
            render newValue
        }
    }
}