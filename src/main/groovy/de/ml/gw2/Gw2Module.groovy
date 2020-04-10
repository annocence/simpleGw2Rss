package de.ml.gw2

import com.google.inject.AbstractModule
import groovy.transform.CompileStatic

@CompileStatic
class Gw2Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(PlayGw2Evaluator)
    }
}
