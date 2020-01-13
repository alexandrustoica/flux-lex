package org.flux.lexical.symbol

internal data class LexicalSymbol(
        override val value: String,
        override val index: Int? = null) : Symbol