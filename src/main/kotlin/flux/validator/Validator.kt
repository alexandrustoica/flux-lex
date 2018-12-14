package flux.validator

import flux.domain.Location


abstract class Validator<T, E> constructor(
        open val accumulator: MutableList<E>) {

    abstract fun add(element: T, location: Location,
                     error: (T, Location) -> Exception): Validator<T, E>

}