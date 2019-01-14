package org.flux.lexical

internal data class LexicalSymbol(
        override val value: String,
        override val index: Int? = null) : Symbol