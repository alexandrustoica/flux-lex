package org.flux.lexical

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

/**
 * @author Alexandru Stoica
 */

internal class SymbolTableTest {

    @Test
    fun `when generating symbol table for simple code expect correct symbol table`() {
        // given:
        val code = "String test = \"test\";"
        val subject = listOf(LexicalSymbol("test", 0))
        // when:
        val result = LexicalSymbolTable(Tokens(Line(value = code, index = 0)))
        // then:
        assertThat(result).containsExactlyElementsOf(subject)
    }

    @Test
    fun `when generating symbol table for file with multiple lines expect correct symbol table`() {
        // given:
        val subject = listOf(
                LexicalSymbol("integer", 0),
                LexicalSymbol("3", 1),
                LexicalSymbol("string", 2),
                LexicalSymbol("Test", 3),
                LexicalSymbol("console", 4),
                LexicalSymbol("document", 5),
                LexicalSymbol("test", 6),
                LexicalSymbol("2", 7),
                LexicalSymbol("works", 8),
                LexicalSymbol("index", 9),
                LexicalSymbol("1", 10),
                LexicalSymbol("10", 11),
                LexicalSymbol("element", 12))
        // when:
        val result = LexicalSymbolTable(File({}.javaClass
                .getResource("/source.flux").toURI()))
        // then:
        assertThat(result).containsExactlyElementsOf(subject)
    }
}