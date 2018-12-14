package flux.validator

import flux.domain.Location

class DynamicAccumulatorValidator<T>(
        override val accumulator: MutableList<Exception>) :
        Validator<T, Exception>(accumulator) {

    override fun add(element: T, location: Location,
                     error: (T, Location) -> Exception): Validator<T, Exception> =
            accumulator.add(error(element, location)).let { this }

}