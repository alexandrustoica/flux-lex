package org.flux.lexical

import java.util.*

internal sealed class LexicalToken : Token {

    private companion object {
        private val keyWords: Properties = Properties().apply {
            {}.javaClass.getResourceAsStream("/keywords.properties")
                    .use { file -> load(file) }
        }
    }

    internal data class KeyWord(
            override val value: String,
            override val code: Int) : LexicalToken() {

        companion object {
            fun of(value: String): KeyWord =
                    KeyWord(value, "${keyWords[value]}".toInt())
        }

        override fun asSymbol(): Symbol? = null
    }

    internal data class Identifier(
            override val value: String) : LexicalToken() {
        override val code: Int = 0
        override fun asSymbol(): Symbol? = LexicalSymbol(value)
    }

    internal data class Constant(
            override val value: String) : LexicalToken() {
        override val code: Int = 1
        override fun asSymbol(): Symbol? = LexicalSymbol(value)
    }

    internal data class Unknown(private val source: String) : LexicalToken() {
        override val value: String = "Unknown: $source"
        override val code: Int = -2
        override fun asSymbol(): Symbol? = null
    }

    internal data class Space(override val value: String) : LexicalToken() {
        override val code: Int = -3
        override fun asSymbol(): Symbol? = null
    }
}