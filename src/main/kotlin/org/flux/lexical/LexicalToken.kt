package org.flux.lexical

internal sealed class LexicalToken : LexToken
internal data class KeyWordToken(
        override val value: String,
        override val code: Int) : LexicalToken()

internal data class IdentifierToken(
        override val value: String) : LexicalToken() {
    override val code: Int = 0
}

internal data class ConstantToken(
        override val value: String) : LexicalToken() {
    override val code: Int = 1
}