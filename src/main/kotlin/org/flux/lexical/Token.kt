package org.flux.lexical

internal interface Token {
    val value: String
    val code: Int
    fun asSymbol(): Symbol?
}