package org.flux.lexical.symbol

import org.flux.lexical.token.Token

internal interface SymbolTable: Iterable<Symbol> {
    fun find(token: Token): Symbol?
}