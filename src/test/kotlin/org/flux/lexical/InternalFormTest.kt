package org.flux.lexical

import org.junit.jupiter.api.Test
import java.io.File

/**
 * @author Alexandru Stoica
 */

internal class InternalFormTest {

    @Test
    fun `when generating internal form from file expect correct internal form`() {
        // given:
        // when:
        val result = InternalForm(File({}.javaClass
                .getResource("/source.flux").toURI()))

        result.forEach { println(it) }
        // then:
    }
}