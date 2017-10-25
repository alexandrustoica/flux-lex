package flux.validator



/**
 * @author Alexandru Stoica
 * @version 1.0
 */


class PropertyNameException(override val message: String): Exception(message)

class Location(val line: Int, val index: Int)

class IdentifierValidator(override val accumulator: MutableList<PropertyNameException>):
        Validator<String, PropertyNameException>(accumulator) {

    companion object {
        private val REGEX_IDENTIFIER = "\\b(_|[a-z]|[A-Z])((_|[a-z]|[A-Z]|\\d){0,249})\\b"
    }

    private val message: (Location) -> String =
            { location -> "ERROR: Invalid identifier name at ${location.line}::${location.index}!" }

    override fun validate(element: String, location: Location): Validator<String, PropertyNameException> {
        return if (element.matches(REGEX_IDENTIFIER.toRegex())) this else
            accumulator.add(PropertyNameException(message(location))).let { this }
    }
}

abstract class Validator<T, E> constructor(open val accumulator: MutableList<E>) {
    abstract fun validate(element: T, location: Location): Validator<T, E>
}