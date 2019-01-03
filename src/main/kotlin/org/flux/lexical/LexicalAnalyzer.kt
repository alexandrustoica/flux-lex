package org.flux.lexical

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

}
