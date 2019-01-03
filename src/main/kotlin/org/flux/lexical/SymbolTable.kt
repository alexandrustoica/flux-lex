package org.flux.lexical

internal interface SymbolTable: Iterable<Symbol> {
    fun find(token: Token): Symbol?
}