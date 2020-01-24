package org.flux.lexical.symbol

import org.flux.lexical.token.Line
import org.flux.lexical.token.Token
import org.flux.lexical.token.Tokens
import java.io.File

internal class LexicalSymbolTable(
        private val tokens: Iterable<Token>) : SymbolTable {

    constructor(lines: List<Line>) :
            this(Tokens(lines))

    constructor(source: File) :
            this(source.readLines()
                    .mapIndexed { index, line -> Line(line, index) }
                    .toList())

    private val symbols: Map<String, Symbol> by lazy {
        tokens.mapNotNull { it.asSymbol() }
                .toSet()
                .mapIndexed { index, symbol ->
                    symbol.value to LexicalSymbol(symbol.value, index)
                }
                .toMap()
    }

    override fun iterator(): Iterator<Symbol> =
            symbols.values.iterator()

    override fun find(token: Token): Symbol? = symbols[token.value]
}