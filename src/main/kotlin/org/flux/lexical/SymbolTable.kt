package org.flux.lexical

internal class SymbolTable(private val tokens: Iterable<Token>) : Iterable<Symbol> {

    constructor(lines: List<Line>):
            this(Tokens(lines))

    private val symbols: Map<Int, Symbol> by lazy {
        tokens.mapNotNull { it.asSymbol() }
                .toSet()
                .mapIndexed { index, symbol ->
                    index to LexicalSymbol(symbol.value, index)
                }
                .toMap()
    }

    override fun iterator(): Iterator<Symbol> =
            symbols.values.iterator()
}