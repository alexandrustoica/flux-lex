package flux.analyzer

import flux.validator.Location
import flux.validator.TokenValidator
import flux.validator.Validator
import java.io.File

/**
 * @author Alexandru Stoica
 * @version 1.0
 */

class Finder {
    fun findAllIn(source: String, basedOnRegex: Regex): List<String> =
            basedOnRegex.findAll(source).map { it.groupValues }.map { it.first() }.toList()
}

class LexicalAnalyzer constructor(private val source: File,
                                  val symbolTable: HashMap<Int, String>,
                                  val internalForm: MutableList<Pair<Int, Int>>) : Analyzer {

    constructor(source: File) : this(source, hashMapOf(), mutableListOf())

    val validator: Validator<String, Exception> = TokenValidator(mutableListOf())

    private val keyWords: HashMap<String, Int> = hashMapOf(
            "Int" to 2, "String" to 3, "List" to 4, "type" to 5, "union" to 6, "print" to 7,
            "read" to 8, "if" to 9, "else" to 10, "for" to 11, "range" to 12,
            "def" to 13, "return" to 14, "->" to 15, "+" to 16, "-" to 17,
            "/" to 18, "*" to 19, ";" to 20, ":" to 21, "=" to 22, "==" to 23,
            "!=" to 24, "!" to 25, ">=" to 26, "<=" to 27, "<" to 28, ">" to 29,
            "(" to 30, ")" to 31, "," to 32, "\"" to 33, "\'" to 34, "//" to 35,
            "{" to 36, "}" to 37
    )

    companion object {
        private const val REGEX_RELATION = "!=|==|>=|<=|<|>"
        private const val REGEX_IDENTIFIER = "\\b(_|[a-z]|[A-Z])((_|[a-z]|[A-Z]|\\d){0,249})\\b"
        private const val REGEX_CONST_STRING = "\"[^\"]+\""
        private const val REGEX_SPECIAL_OPERATORS = "->"
        private const val REGEX_MATH_OPERATORS = "\\+|-|/|\\*|%"
        private const val REGEX_SEPARATORS = "[ (){}:;\",\\t]"
        private const val REGEX_ATTRIBUTION = "="
        private const val REGEX_OPERATORS = "$REGEX_SPECIAL_OPERATORS|$REGEX_RELATION|$REGEX_MATH_OPERATORS"
        private const val REGEX_NUMBERS = "\\d+"
        private const val REGEX_TOKENS = "$REGEX_CONST_STRING|(\\w+)|$REGEX_OPERATORS|" +
                "$REGEX_ATTRIBUTION|$REGEX_SEPARATORS|$REGEX_NUMBERS"
    }

    fun analyze(): LexicalAnalyzer =
            source.readLines().map { getAtomsFrom(it) }
                    .forEachIndexed { index, line -> analyzeLine(line, index + 1) }.let { this }

    private fun analyzeLine(line: List<String>, indexLine: Int) {
        line.forEachIndexed { indexWord, token -> analyzeToken(token, indexWord + 1, indexLine) }
    }

    private fun analyzeToken(token: String, indexWord: Int, indexLine: Int) {
        when {
            isTokenKeyWord(token) -> saveKeyTokenToInternalForm(checkSingleQuotesToken(token, Location(indexLine, indexWord)))
            isTokenStringConstant(token) ->
                saveConstantStringTokenToInternalForm(saveConstantStringTokenToTableSymbols(token))
            isTokenNumberConstant(token) ->
                saveConstantNumberTokenToInternalForm(saveTokenToTableSymbols(token))
            isTokenSpaceSeparator(token) -> { }
            isTokenIdentifier(token) -> saveIdentifierTokenToInternalForm(saveTokenToTableSymbols(token))
            else -> validator.validate(token, Location(indexLine, indexWord))
        }
    }

    private fun checkSingleQuotesToken(token: String, location: Location): String =
            if (token.matches("\"".toRegex()))
                validator.add("ERROR: Invalid open quotes", location).let{token} else token

    private fun isTokenIdentifier(token: String): Boolean = token.matches(REGEX_IDENTIFIER.toRegex())

    private fun saveKeyTokenToInternalForm(token: String) =
            internalForm.add(Pair(keyWords[token]!!, -1))

    private fun saveConstantNumberTokenToInternalForm(code: Int) =
            internalForm.add(Pair(1, code))

    private fun saveConstantStringTokenToInternalForm(code: Int) =
            internalForm.addAll(listOf(Pair(keyWords["\""]!!, -1),
                    Pair(1, code), Pair(keyWords["\""]!!, -1)))

    private fun saveConstantStringTokenToTableSymbols(token: String): Int =
            if (symbolTable.containsValue(token)) getKeyForSymbol(token)
            else symbolTable.put(symbolTable.size, token).let { getKeyForSymbol(token) }

    private fun saveIdentifierTokenToInternalForm(code: Int) =
            internalForm.add(Pair(0, code))

    private fun saveTokenToTableSymbols(token: String): Int =
            if (symbolTable.containsValue(token)) getKeyForSymbol(token)
            else symbolTable.put(symbolTable.size, token).let {  getKeyForSymbol(token) }

    private fun getKeyForSymbol(symbol: String): Int =
            symbolTable.filterValues { it == symbol }.toList().first().first

    private fun isTokenKeyWord(token: String): Boolean = keyWords.keys.contains(token)

    private fun isTokenSpaceSeparator(token: String): Boolean = token.matches("\\s|\\t".toRegex())

    private fun isTokenStringConstant(token: String): Boolean =
            token.matches(REGEX_CONST_STRING.toRegex())

    private fun isTokenNumberConstant(token: String): Boolean = token.matches(REGEX_NUMBERS.toRegex())

    private fun getAtomsFrom(source: String): List<String> = Finder().findAllIn(source, REGEX_TOKENS.toRegex())
}
