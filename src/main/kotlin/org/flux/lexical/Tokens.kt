package org.flux.lexical

import java.util.*

internal class Tokens(
        private val source: Sequence<String>) : Iterable<Token> {

    private companion object {
        private val patterns: Properties = Properties().apply {
            {}.javaClass.getResourceAsStream("/patterns.properties")
                    .use { file -> load(file) }
        }

        private val spacePattern: Regex = Regex("\\s")

        private val identifierPattern: Regex =
                Regex("${patterns["identifier"]}")

        private val constantStringPattern: Regex =
                Regex("${patterns["constant.string"]}")

        private val constantNumberPattern: Regex =
                Regex("${patterns["constant.number"]}")
    }

    constructor(source: Line) : this(source.tokens())

    constructor(source: Iterable<Line>) :
            this(source.asSequence().flatMap { it.tokens() })

    private val keyWords: Properties = Properties().apply {
        {}.javaClass.getResourceAsStream("/keywords.properties")
                .use { file -> load(file) }
    }

    override fun iterator(): Iterator<Token> =
            source.toList().flatMap {
                when {
                    keyWords.keys.contains(it) ->
                        listOf(LexicalToken.KeyWord.of(it))
                    spacePattern.matches(it) -> listOf()
                    constantStringPattern.matches(it) ->
                        listOf(LexicalToken.KeyWord.of("\""),
                                LexicalToken.Constant(it.removeSurrounding("\"")),
                                LexicalToken.KeyWord.of("\""))
                    constantNumberPattern.matches(it) ->
                        listOf(LexicalToken.Constant(it))
                    identifierPattern.matches(it) ->
                        listOf(LexicalToken.Identifier(it))
                    else -> listOf(LexicalToken.Unknown(it))
                }
            }.iterator()

}
