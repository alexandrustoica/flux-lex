package flux.analyzer

import flux.domain.Location
import flux.domain.SymbolTable
import flux.exception.OpenQuotesException
import flux.exception.PropertyNameException
import flux.validator.DynamicAccumulatorValidator
import flux.validator.Validator
import java.io.File
import java.io.FileInputStream
import java.util.*


class LexicalAnalyzer constructor(
        private val source: File,
        private val symbolTable: SymbolTable<String, Int>,
        val internalForm: MutableList<Pair<Int, Int>>) {

    val validator: Validator<String, Exception> = DynamicAccumulatorValidator(
            mutableListOf())

    private val keyWords = Properties().apply {
        FileInputStream(File("keywords.properties")).use { file -> load(file) }
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

    constructor(source: File) : this(source, SymbolTable(), mutableListOf())

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
            symbolTable.getOrPut(token) { symbolTable.size() }

    private fun saveIdentifierTokenToInternalForm(code: Int) =
            internalForm.add(Pair(0, code))

    private fun saveTokenToTableSymbols(token: String): Int =
            symbolTable.getOrPut(token) { symbolTable.size() }

    private fun isTokenKeyWord(token: String): Boolean =
            keyWords.keys.contains(token)

    private fun isTokenSpaceSeparator(token: String): Boolean =
            token.matches("\\s|\\t".toRegex())

    private fun isTokenStringConstant(token: String): Boolean =
            token.matches(REGEX_CONST_STRING.toRegex())

    private fun isTokenNumberConstant(token: String): Boolean =
            token.matches(REGEX_NUMBERS.toRegex())

    private fun getAtomsFrom(source: String): List<String> =
            Finder().findAllIn(source, REGEX_TOKENS.toRegex())

}
