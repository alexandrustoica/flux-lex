package org.flux.lexical

import flux.exception.OpenQuotesException
import flux.exception.PropertyNameException
import flux.validator.FluxLexicalErrorAccumulator
import flux.validator.FluxLexicalErrors
import java.io.File
import java.util.*

internal class LexicalAnalyzer constructor(
        private val source: File,
        private val identifiers: MutableMap<String, Int>,
        private val constants: MutableMap<String, Int>,
        private val internalForm: MutableList<InternalFormRecord>) {

    private val validator: FluxLexicalErrors<TokenWithLocation> = FluxLexicalErrorAccumulator()

    private val keyWords: Properties = Properties().apply {
        {}.javaClass.getResourceAsStream("/keywords.properties").use { file -> load(file) }
    }

    companion object {
        private const val REGEX_IDENTIFIER =
                "\\b(_|[a-z]|[A-Z])((_|[a-z]|[A-Z]|\\d){0,249})\\b"
        private const val REGEX_CONST_STRING = "\"[^\"]+\""
        private const val REGEX_NUMBERS = "\\d+"
    }

    constructor(source: File) :
            this(source, mutableMapOf(), mutableMapOf(), mutableListOf())

    internal data class LexicalResult(
            val errors: List<Exception>,
            val identifiers: Map<String, Int>,
            val constants: Map<String, Int>,
            val internalForm: List<InternalFormRecord>)

//    fun analyze(): LexicalResult {
//        source.readLines()
//                .mapIndexed { index, value -> Line(value, index) }
//                .forEach { analyze(line = it) }
//        return LexicalResult(
//                errors = validator.errors(),
//                identifiers = identifiers,
//                constants = constants,
//                internalForm = internalForm)
//    }
//
//    private fun analyze(line: Line) {
//        line.tokens().forEach { analyze(it) }
//    }

    private fun analyze(token: TokenWithLocation) {
        when {
            isTokenKeyWord(token.value) ->
                checkSingleQuotesToken(token).also { saveKeyTokenToInternalForm(it) }
            isTokenStringConstant(token.value) ->
                saveConstantStringTokenToInternalForm(
                        constants.getOrPut(token.value) { constants.size })
            isTokenNumberConstant(token.value) ->
                saveConstantNumberTokenToInternalForm(
                        constants.getOrPut(token.value) { constants.size })
            isTokenSpaceSeparator(token.value) -> {
            }
            isTokenIdentifier(token.value) -> saveIdentifierTokenToInternalForm(
                    identifiers.getOrPut(token.value) { identifiers.size })
            else -> validator.add(token)
            { PropertyNameException(token.value, token.location) }
        }
    }

    private fun checkSingleQuotesToken(token: TokenWithLocation): String =
            if (token.value.matches("\"".toRegex()))
                validator.add(token)
                { OpenQuotesException(token.location) }.let { token.value }
            else token.value

    private fun isTokenIdentifier(token: String): Boolean =
            token.matches(REGEX_IDENTIFIER.toRegex())

    private fun saveKeyTokenToInternalForm(token: String) =
            internalForm.add(InternalFormRecord(KeyWord((keyWords[token] as String).toInt(), token), -1))

    private fun saveConstantNumberTokenToInternalForm(code: Int) =
            internalForm.add(InternalFormRecord(KeyWord(1, "constant"), code))

    private fun saveConstantStringTokenToInternalForm(code: Int) =
            internalForm.addAll(
                    listOf(InternalFormRecord(KeyWord((keyWords["\""] as String).toInt(), "\""), -1),
                            InternalFormRecord(KeyWord(1, "constant"), code),
                            InternalFormRecord(KeyWord((keyWords["\""] as String).toInt(), "\""), -1)))

    private fun saveIdentifierTokenToInternalForm(code: Int): Boolean =
            internalForm.add(InternalFormRecord(KeyWord(0, "identifier"), code))


    private fun isTokenKeyWord(token: String): Boolean =
            keyWords.keys.contains(token)

    private fun isTokenSpaceSeparator(token: String): Boolean =
            token.matches("\\s|\\t".toRegex())

    private fun isTokenStringConstant(token: String): Boolean =
            token.matches(REGEX_CONST_STRING.toRegex())

    private fun isTokenNumberConstant(token: String): Boolean =
            token.matches(REGEX_NUMBERS.toRegex())

}
