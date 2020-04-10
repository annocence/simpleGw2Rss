package de.ml.gw2

import ratpack.handling.Context
import ratpack.handling.Handler

class Errorhandler implements Handler{
    @Override
    void handle(Context ctx) throws Exception {
        render   "error"
    }
}
