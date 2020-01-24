package org.flux.lexical.token

import org.flux.lexical.symbol.Symbol

internal interface Token {
    val value: String
    val code: Int
    fun asSymbol(): Symbol?
}