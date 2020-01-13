package org.flux.lexical.token

import java.util.*

internal data class Line(
        private val value: String,
        private val index: Int) {

    private val patterns: Properties = Properties().apply {
        {}.javaClass.getResourceAsStream("/patterns.properties")
                .use { file -> load(file) }
    }

    fun tokens(): Sequence<String> =
            Regex(patterns["tokens"].toString())
                    .findAll(value)
                    .map { it.groupValues }
                    .map { it.first() }
}