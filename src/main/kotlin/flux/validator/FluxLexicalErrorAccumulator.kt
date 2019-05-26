package flux.validator

import flux.domain.Location

internal class FluxLexicalErrorAccumulator<T>(
        private val accumulator: MutableList<Exception> = mutableListOf()) :
        FluxLexicalErrors<T> {

    override fun errors(): List<Exception> = accumulator

    override fun add(element: T, location: Location,
                     error: (T, Location) -> Exception): FluxLexicalErrors<T> =
            accumulator.add(error(element, location)).let { this }

}