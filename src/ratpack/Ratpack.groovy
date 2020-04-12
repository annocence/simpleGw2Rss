import de.ml.gw2.GiphyRenderer
import de.ml.gw2.Gw2Module
import de.ml.gw2.PlayGw2Evaluator
import ratpack.exec.Promise
import ratpack.handling.Context
import ratpack.http.client.HttpClient
import ratpack.http.client.ReceivedResponse


import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module Gw2Module
    }
    handlers {
        get("playGw2") { PlayGw2Evaluator playGw2Evaluator, GiphyRenderer giphyRenderer ->
            getResponse().contentType("text/html")
            render giphyRenderer.render(playGw2Evaluator.evaluatePlayGw2())
        }

        get("set/:dailyanswer") { ctx ->
            Optional<String> authML = context.header(System.getenv("AUTH_ANSWER_HEADER"))
            if (System.getenv("AUTH_ANSWER_PW") == authML.orElse("")) {
                ctx.next()
            } else {
                render ""
            }
        }

        get("set/:dailyanswer") { PlayGw2Evaluator playGw2Evaluator ->
            def newValue = context.getPathTokens().get("dailyanswer")
            playGw2Evaluator.setDailyMessage(newValue)
            render newValue
        }

        all() {
            render ""
        }

    }
}