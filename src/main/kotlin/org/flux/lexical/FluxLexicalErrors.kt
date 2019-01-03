package org.flux.lexical

interface FluxLexicalErrors<T> {
    fun errors(): List<Exception>
    fun add(element: T, error: (T) -> Exception): FluxLexicalErrors<T>
}