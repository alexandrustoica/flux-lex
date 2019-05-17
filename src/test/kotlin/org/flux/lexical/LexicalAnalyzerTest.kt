package org.flux.lexical

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

/**
 * @author Alexandru Stoica
 */

internal class LexicalAnalyzerTest {

    @Test
    fun `when analyzing source file expect correct internal form`() {
        // given:
        val subject = "InternalFormRecord(keyWord=KeyWord(code=2, value=Int), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=0, value=identifier), tableSymbolKey=0)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=4, value==), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=1, value=constant), tableSymbolKey=1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=5, value=;), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=3, value=String), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=0, value=identifier), tableSymbolKey=2)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=4, value==), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=27, value=\"), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=1, value=constant), tableSymbolKey=3)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=27, value=\"), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=5, value=;), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=3, value=String), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=0, value=identifier), tableSymbolKey=4)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=4, value==), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=7, value=read), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=10, value=(), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=11, value=)), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=5, value=;), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=3, value=String), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=0, value=identifier), tableSymbolKey=5)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=4, value==), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=6, value=print), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=10, value=(), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=27, value=\"), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=1, value=constant), tableSymbolKey=6)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=27, value=\"), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=11, value=)), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=5, value=;), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=8, value=if), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=10, value=(), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=0, value=identifier), tableSymbolKey=0)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=18, value=>=), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=1, value=constant), tableSymbolKey=1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=11, value=)), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=12, value={), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=6, value=print), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=10, value=(), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=27, value=\"), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=1, value=constant), tableSymbolKey=7)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=27, value=\"), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=11, value=)), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=5, value=;), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=13, value=}), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=9, value=else), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=8, value=if), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=10, value=(), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=0, value=identifier), tableSymbolKey=0)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=19, value=<=), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=1, value=constant), tableSymbolKey=8)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=11, value=)), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=12, value={), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=6, value=print), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=10, value=(), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=27, value=\"), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=1, value=constant), tableSymbolKey=6)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=27, value=\"), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=11, value=)), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=5, value=;), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=13, value=}), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=9, value=else), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=12, value={), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=6, value=print), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=10, value=(), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=27, value=\"), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=1, value=constant), tableSymbolKey=9)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=27, value=\"), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=11, value=)), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=5, value=;), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=13, value=}), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=24, value=for), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=10, value=(), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=2, value=Int), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=0, value=identifier), tableSymbolKey=10)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=26, value=:), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=25, value=range), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=10, value=(), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=1, value=constant), tableSymbolKey=11)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=26, value=:), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=1, value=constant), tableSymbolKey=12)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=11, value=)), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=11, value=)), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=12, value={), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=13, value=}), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=24, value=for), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=10, value=(), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=3, value=String), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=0, value=identifier), tableSymbolKey=13)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=26, value=:), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=0, value=identifier), tableSymbolKey=2)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=11, value=)), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=12, value={), tableSymbolKey=-1)\n" +
                "InternalFormRecord(keyWord=KeyWord(code=13, value=}), tableSymbolKey=-1)"
        // when:
        val result = LexicalAnalyzer(File({}.javaClass.getResource("/source.flux").toURI()))
                .analyze().internalForm
        // then:
        Assertions.assertEquals(result.joinToString("\n"), subject)
    }
}