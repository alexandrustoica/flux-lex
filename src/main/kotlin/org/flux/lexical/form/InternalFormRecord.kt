package org.flux.lexical.form

import org.flux.lexical.token.Token


internal data class InternalFormRecord(
        val value: Token,
        val symbolKey: Int? = null)