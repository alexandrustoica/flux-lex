package org.flux.lexical

import flux.domain.Location
import flux.exception.OpenQuotesException
import flux.exception.PropertyNameException
import flux.validator.FluxLexicalErrorAccumulator
import flux.validator.FluxLexicalErrors
import java.io.File
import java.util.*


class LexicalAnalyzer constructor(
        private val source: File,
        private val table: MutableMap<String, Int>,
        val internalForm: MutableList<Pair<Int, Int>>) {

    val validator: FluxLexicalErrors<String> = FluxLexicalErrorAccumulator()

    private val keyWords = Properties().apply {
        {}.javaClass.getResourceAsStream("/keywords.properties").use { file -> load(file) }
    }

    companion object {
        private const val REGEX_RELATION = "!=|==|>=|<=|<|>"
        private const val REGEX_IDENTIFIER =
                "\\b(_|[a-z]|[A-Z])((_|[a-z]|[A-Z]|\\d){0,249})\\b"
        private const val REGEX_CONST_STRING = "\"[^\"]+\""
        private const val REGEX_SPECIAL_OPERATORS = "->"
        private const val REGEX_MATH_OPERATORS = "\\+|-|/|\\*|%"
        private const val REGEX_SEPARATORS = "[ (){}:;\",\\t]"
        private const val REGEX_ATTRIBUTION = "="
        private const val REGEX_OPERATORS =
                "$REGEX_SPECIAL_OPERATORS|$REGEX_RELATION|$REGEX_MATH_OPERATORS"
        private const val REGEX_NUMBERS = "\\d+"
        private const val REGEX_TOKENS =
                "$REGEX_CONST_STRING|(\\w+)|$REGEX_OPERATORS|" +
                        "$REGEX_ATTRIBUTION|$REGEX_SEPARATORS|$REGEX_NUMBERS"
    }

    constructor(source: File) : this(source, mutableMapOf(), mutableListOf())

    fun analyze(): LexicalAnalyzer =
            source.readLines().map { getAtomsFrom(it) }
                    .forEachIndexed { index, line ->
                        analyzeLine(line, index + 1)
                    }.let { this }

    private fun analyzeLine(line: List<String>, indexLine: Int) {
        line.forEachIndexed { indexWord, token ->
            analyzeToken(token, Location(indexWord + 1, indexLine))
        }
    }

    private fun analyzeToken(token: String, location: Location) {
        when {
            isTokenKeyWord(token) ->
                checkSingleQuotesToken(token,
                        location).also { saveKeyTokenToInternalForm(it) }
            isTokenStringConstant(token) ->
                saveConstantStringTokenToInternalForm(
                        saveConstantStringTokenToTableSymbols(token))
            isTokenNumberConstant(token) ->
                saveConstantNumberTokenToInternalForm(
                        saveTokenToTableSymbols(token))
            isTokenSpaceSeparator(token) -> {
            }
            isTokenIdentifier(token) -> saveIdentifierTokenToInternalForm(
                    saveTokenToTableSymbols(token))
            else -> validator.add(token, location)
            { _, _ -> PropertyNameException(token, location) }
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
            internalForm.add(Pair((keyWords[token] as String).toInt(), -1))

    private fun saveConstantNumberTokenToInternalForm(code: Int) =
            internalForm.add(Pair(1, code))

    private fun saveConstantStringTokenToInternalForm(code: Int) =
            internalForm.addAll(
                    listOf(Pair((keyWords["\""] as String).toInt(), -1),
                            Pair(1, code),
                            Pair((keyWords["\""] as String).toInt(), -1)))

    private fun saveConstantStringTokenToTableSymbols(token: String): Int =
            table.getOrPut(token) { table.count() }

    private fun saveIdentifierTokenToInternalForm(code: Int) =
            internalForm.add(Pair(0, code))

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

    private fun getAtomsFrom(source: String): List<String> =
            REGEX_TOKENS.toRegex().findAll(source)
                    .map { it.groupValues }
                    .map { it.first() }
                    .toList()

}