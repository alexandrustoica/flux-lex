package flux.validator

import flux.domain.Location

/**
 * @author Alexandru Stoica
 * @version 1.0
 */

class DynamicAccumulatorValidator<T>(override val accumulator: MutableList<Exception>) :
        Validator<T, Exception>(accumulator) {

    override fun add(element: T, location: Location, error: (T, Location) -> Exception): Validator<T, Exception> {
        return accumulator.add(error(element, location)).let { this }
    }
}

abstract class Validator<T, E> constructor(open val accumulator: MutableList<E>) {
    abstract fun add(element: T, location: Location, error: (T, Location) -> Exception): Validator<T, E>
}