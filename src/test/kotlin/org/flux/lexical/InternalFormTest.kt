package org.flux.lexical

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

/**
 * @author Alexandru Stoica
 */

internal class InternalFormTest {

    internal data class InternalFormToSimpleList(
            private val records: Iterable<InternalFormRecord>) : Iterable<String> {

        override fun iterator(): Iterator<String> = records
                .map { "${it.value.code} ${it.symbolKey ?: -1}" }
                .iterator()
    }

    @Test
    fun `when generating internal form from file expect correct internal form`() {
        // given:
        val subject = {}.javaClass
                .getResource("/result-internal-form.txt")
                .readText().lines()
        // when:
        val result = InternalFormToSimpleList(
                InternalForm(File({}.javaClass
                        .getResource("/source.flux").toURI())))
        // then:
        assertThat(result).containsExactlyElementsOf(subject)
    }
}