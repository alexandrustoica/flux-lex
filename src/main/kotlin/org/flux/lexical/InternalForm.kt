package org.flux.lexical

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.File

internal class InternalForm(
        @JsonIgnore
        private val tokens: Tokens,

        @JsonIgnore
        private val table: SymbolTable) : Iterable<InternalFormRecord> {

    constructor(source: File) :
            this(Tokens(source.readLines()
                    .mapIndexed { index, line -> Line(line, index) }),
                    LexicalSymbolTable(source))

    override fun iterator(): Iterator<InternalFormRecord> =
            tokens.map { InternalFormRecord(it, table.find(it)?.index) }
                    .iterator()

}