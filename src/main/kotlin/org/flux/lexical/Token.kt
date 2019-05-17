package org.flux.lexical

import flux.domain.Location

internal data class Token(
        val value: String,
        val location: Location)