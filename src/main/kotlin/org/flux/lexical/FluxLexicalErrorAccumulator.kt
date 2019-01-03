package org.flux.lexical

internal class FluxLexicalErrorAccumulator<T>(
        private val accumulator: MutableList<Exception> = mutableListOf()) :
        FluxLexicalErrors<T> {

    override fun errors(): List<Exception> = accumulator

    override fun add(element: T, error: (T) -> Exception): FluxLexicalErrors<T> =
            accumulator.add(error(element)).let { this }

}