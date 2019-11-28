package flux.analyzer

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.jupiter.api.Test

/**
 * @author Alexandru Stoica
 */

internal class FinderTest {

    @Test
    fun `when true then true`() {
        // given:
        val subject = true
        // when:
        val result = true
        // then:
        Assert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(subject)))
    }
}