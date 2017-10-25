package flux.validator

import org.junit.Assert

/**
 * @author Alexandru Stoica
 * @version 1.0
 */

class IdentifierValidatorTest {

    @org.junit.Test
    fun validate() {
        val validator =
                IdentifierValidator(mutableListOf()).validate("3_ana", Location(1, 1))
        Assert.assertTrue(validator.accumulator.size == 1)
    }

}