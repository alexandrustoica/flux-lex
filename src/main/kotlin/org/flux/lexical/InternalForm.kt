package org.flux.lexical

import java.io.File

internal class InternalForm(
        private val tokens: Tokens,
        private val table: SymbolTable) : Iterable<InternalFormRecord> {

    constructor(source: File) :
            this(Tokens(source.readLines()
                    .mapIndexed { index, line -> Line(line, index) }),
                    LexicalSymbolTable(source))

    override fun iterator(): Iterator<InternalFormRecord> = tokens
            .map { InternalFormRecord(it, table.find(it)?.index) }
            .iterator()

}