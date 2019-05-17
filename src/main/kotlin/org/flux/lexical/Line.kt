package org.flux.lexical

import flux.domain.Location

internal data class Line(
        private val value: String,
        private val index: Int) {

    fun tokens(): List<Token> = AtomsFromLine(value)
            .mapIndexed { indexToken, value ->
                Token(value, Location(index, indexToken))
            }

}