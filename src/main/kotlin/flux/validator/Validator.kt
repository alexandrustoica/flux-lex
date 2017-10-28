package flux.validator



/**
 * @author Alexandru Stoica
 * @version 1.0
 */


public class PropertyNameException(override val message: String): Exception(message)
public class SpecialException(override val message: String): Exception(message)

public class Location(val line: Int, val index: Int)

public class TokenValidator(override val accumulator: MutableList<Exception>):
        Validator<String, Exception>(accumulator) {

    companion object {
        private val REGEX_IDENTIFIER = "\\b(_|[a-z]|[A-Z])((_|[a-z]|[A-Z]|\\d){0,249})\\b"
    }

    private val message: (Location) -> String =
            { location -> "ERROR: Invalid identifier name at ${location.line}::${location.index}!" }

    override fun validate(identifier: String, location: Location): Validator<String, Exception> =
            if (identifier.matches(REGEX_IDENTIFIER.toRegex())) this
            else accumulator.add(PropertyNameException(message(location))).let { this }

    override fun add(error: String, location: Location): Validator<String, Exception> =
            accumulator.add(SpecialException("$error at ${location.line}::${location.index}!")).let { this }
}

abstract class Validator<T, E> constructor(open val accumulator: MutableList<E>) {
    abstract fun validate(element: T, location: Location): Validator<T, E>
    abstract fun add(error: String, location: Location): Validator<T, E>
}