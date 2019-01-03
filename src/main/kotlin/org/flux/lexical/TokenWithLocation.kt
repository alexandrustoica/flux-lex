package org.flux.lexical


internal data class TokenWithLocation(
        val token: Token,
        val location: Location) : Token {

    override val value: String = token.value
    override val code: Int = token.code

    override fun asSymbol(): Symbol? = token.asSymbol()
}