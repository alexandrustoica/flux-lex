package flux.analyzer

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

    // TODO: SymbolTable Double Constants

    constructor(source: File) : this(source, hashMapOf(), mutableListOf())

    private val keyWords: HashMap<String, Int> = hashMapOf(
            "Int" to 2, "String" to 3, "List" to 4, "type" to 5, "union" to 6, "print" to 7,
            "read" to 8, "if" to 9, "else" to 10, "for" to 11, "range" to 12,
            "def" to 13, "return" to 14, "->" to 15, "+" to 16, "-" to 17,
            "/" to 18, "*" to 19, ";" to 20, ":" to 21, "=" to 22, "==" to 23,
            "!=" to 24, "!" to 25, ">=" to 26, "<=" to 27, "<" to 28, ">" to 29,
            "(" to 30, ")" to 31, "," to 32, "\"" to 33, "\'" to 34, "//" to 35
    )

    companion object {
        private val REGEX_RELATION = "!=|==|>=|<=|<|>"
        private val REGEX_IDENTIFIER = "\\b(_|[a-z]|[A-Z])((_|[a-z]|[A-Z]|\\d){0,249})\\b"
        private val REGEX_CONST_STRING = "\"[^\"]+\""
        private val REGEX_SPECIAL_OPERATORS = "->"
        private val REGEX_MATH_OPERATORS = "\\+|-|/|\\*|%"
        private val REGEX_SEPARATORS = "[ (){}:;\",\\t]"
        private val REGEX_ATTRIBUTION = "="
        private val REGEX_OPERATORS = "$REGEX_SPECIAL_OPERATORS|$REGEX_RELATION|$REGEX_MATH_OPERATORS"
        private val REGEX_NUMBERS = "\\d+"
        private val REGEX_TOKENS = "$REGEX_CONST_STRING|(\\w+)|$REGEX_OPERATORS|" +
                "$REGEX_ATTRIBUTION|$REGEX_SEPARATORS|$REGEX_NUMBERS"
    }

    fun analyze(): LexicalAnalyzer {
        return source.readLines().map { getAtomsFrom(it) }.forEach { analyzeLine(it) }.let { this }
    }

    private fun analyzeLine(line: List<String>) {
        line.forEachIndexed { index, token -> analyzeToken(line, token, index) }
    }

    private fun analyzeToken(context: List<String>, token: String, index: Int) {
        when {
            isTokenKeyWord(token) -> saveKeyTokenToInternalForm(token)
            isTokenStringConstant(token) ->
                saveConstantStringTokenToInternalForm(saveConstantStringTokenToTableSymbols(token))
            isTokenNumberConstant(token) ->
                saveConstantNumberTokenToInternalForm(saveTokenToTableSymbols(token))
            isTokenSpaceSeparator(token) -> { }
            isTokenIdentifier(token) -> saveIdentifierTokenToInternalForm(saveTokenToTableSymbols(token))
        }
    }

    private fun isTokenIdentifier(token: String): Boolean = token.matches(REGEX_IDENTIFIER.toRegex())

    private fun saveKeyTokenToInternalForm(token: String) =
            internalForm.add(Pair(keyWords[token]!!, -1))

    private fun saveConstantNumberTokenToInternalForm(code: Int) =
            internalForm.add(Pair(1, code))

    private fun saveConstantStringTokenToInternalForm(code: Int) =
            internalForm.addAll(listOf(Pair(keyWords["\""]!!, -1),
                    Pair(1, code), Pair(keyWords["\""]!!, -1)))

    private fun saveConstantStringTokenToTableSymbols(token: String): Int =
            symbolTable.put(symbolTable.size, token.replace("\"", "")).let { symbolTable.size - 1 }

    private fun saveIdentifierTokenToInternalForm(code: Int) =
        internalForm.add(Pair(0, code))

    private fun saveTokenToTableSymbols(token: String): Int =
            symbolTable.put(symbolTable.size, token).let { symbolTable.size - 1 }

    private fun isTokenKeyWord(token: String): Boolean = keyWords.keys.contains(token)

    private fun isTokenSpaceSeparator(token: String): Boolean = token.matches("\\s|\\t".toRegex())

    private fun isTokenStringConstant(token: String): Boolean =
            token.matches(REGEX_CONST_STRING.toRegex())

    private fun isTokenNumberConstant(token: String): Boolean = token.matches(REGEX_NUMBERS.toRegex())

    private fun getAtomsFrom(source: String): List<String> = Finder().findAllIn(source, REGEX_TOKENS.toRegex())
}
