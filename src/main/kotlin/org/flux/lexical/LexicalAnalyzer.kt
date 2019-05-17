package org.flux.lexical

import flux.domain.Location
import flux.exception.OpenQuotesException
import flux.exception.PropertyNameException
import flux.validator.FluxLexicalErrorAccumulator
import flux.validator.FluxLexicalErrors
import java.io.File
import java.util.*


internal class LexicalAnalyzer constructor(
        private val source: File,
        private val table: MutableMap<String, Int>,
        val internalForm: MutableList<InternalFormRecord>) {

    val validator: FluxLexicalErrors<String> = FluxLexicalErrorAccumulator()

    private val keyWords: Properties = Properties().apply {
        {}.javaClass.getResourceAsStream("/keywords.properties").use { file -> load(file) }
    }

    companion object {
        private const val REGEX_IDENTIFIER =
                "\\b(_|[a-z]|[A-Z])((_|[a-z]|[A-Z]|\\d){0,249})\\b"
        private const val REGEX_CONST_STRING = "\"[^\"]+\""
        private const val REGEX_NUMBERS = "\\d+"
    }

    constructor(source: File) : this(source, mutableMapOf(), mutableListOf())

    fun analyze(): LexicalAnalyzer =
            source.readLines()
                    .mapIndexed { index, value -> Line(value, index) }
                    .forEach { analyze(line = it) }
                    .let { this }

    private fun analyze(line: Line) {
        line.tokens().forEach { analyze(it) }
    }

    private fun analyze(token: Token) {
        when {
            isTokenKeyWord(token.value) ->
                checkSingleQuotesToken(token.value,
                        token.location).also { saveKeyTokenToInternalForm(it) }
            isTokenStringConstant(token.value) ->
                saveConstantStringTokenToInternalForm(
                        saveConstantStringTokenToTableSymbols(token.value))
            isTokenNumberConstant(token.value) ->
                saveConstantNumberTokenToInternalForm(
                        saveTokenToTableSymbols(token.value))
            isTokenSpaceSeparator(token.value) -> {
            }
            isTokenIdentifier(token.value) -> saveIdentifierTokenToInternalForm(
                    saveTokenToTableSymbols(token.value))
            else -> validator.add(token.value, token.location)
            { _, _ -> PropertyNameException(token.value, token.location) }
        }
    }

    private fun checkSingleQuotesToken(token: String,
                                       location: Location): String =
            if (token.matches("\"".toRegex()))
                validator.add(token, location)
                { _, _ -> OpenQuotesException(location) }.let { token }
            else token

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

    private fun saveConstantStringTokenToTableSymbols(token: String): Int =
            table.getOrPut(token) { table.count() }

    private fun saveIdentifierTokenToInternalForm(code: Int): Boolean =
            internalForm.add(InternalFormRecord(KeyWord(0, "identifier"), code))

    private fun saveTokenToTableSymbols(token: String): Int =
            table.getOrPut(token) { table.count() }

    private fun isTokenKeyWord(token: String): Boolean =
            keyWords.keys.contains(token)

    private fun isTokenSpaceSeparator(token: String): Boolean =
            token.matches("\\s|\\t".toRegex())

    private fun isTokenStringConstant(token: String): Boolean =
            token.matches(REGEX_CONST_STRING.toRegex())

    private fun isTokenNumberConstant(token: String): Boolean =
            token.matches(REGEX_NUMBERS.toRegex())

}
