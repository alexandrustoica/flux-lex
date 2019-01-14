package org.flux.lexical

import flux.domain.Location


internal data class TokenWithLocation(
        val token: Token,
        val location: Location) : Token {

    override val value: String = token.value
    override val code: Int = token.code

    override fun asSymbol(): Symbol? = token.asSymbol()
}