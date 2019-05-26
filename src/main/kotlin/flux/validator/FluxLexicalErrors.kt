package flux.validator

import flux.domain.Location

interface FluxLexicalErrors<T> {
    fun errors(): List<Exception>
    fun add(element: T, location: Location,
            error: (T, Location) -> Exception): FluxLexicalErrors<T>
}