package de.ml.gw2.config

import com.google.inject.AbstractModule
import de.ml.gw2.GiphyRenderer
import de.ml.gw2.PlayGw2Evaluator
import groovy.transform.CompileStatic

@CompileStatic
class Gw2Module extends AbstractModule {
    @Override
    protected void configure() {
        bind PlayGw2Evaluator
        bind GiphyRenderer
    }
}
