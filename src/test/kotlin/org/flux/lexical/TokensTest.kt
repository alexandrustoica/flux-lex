package org.flux.lexical

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * @author Alexandru Stoica
 */


internal class TokensTest {

    @Test
    fun `when converting string assigment to tokens expect correct tokens`() {
        // given:
        val assignment = "String test = \"test\";"
        val subject = arrayListOf<Token>(
                LexicalToken.KeyWord(value = "String", code = 3),
                LexicalToken.Identifier(value = "test"),
                LexicalToken.KeyWord(value = "=", code = 4),
                LexicalToken.KeyWord(value = "\"", code = 27),
                LexicalToken.Constant(value = "test"),
                LexicalToken.KeyWord(value = "\"", code = 27),
                LexicalToken.KeyWord(value = ";", code = 5))
        // when:
        val result = Tokens(Line(assignment, 0))
        // then:
        assertThat(result).containsAll(subject)
    }

    @Test
    fun `when converting int assignment to tokens expect correct tokens`() {
        // given:
        val assignment = "Int test = 0;"
        val subject = arrayListOf<Token>(
                LexicalToken.KeyWord(value = "Int", code = 2),
                LexicalToken.Identifier(value = "test"),
                LexicalToken.KeyWord(value = "=", code = 4),
                LexicalToken.Constant(value = "0"),
                LexicalToken.KeyWord(value = ";", code = 5)
        )
        // when:
        val result = Tokens(Line(assignment, 0))
        // then:
        assertThat(result).containsAll(subject)
    }

    @Test
    fun `when converting read statement to tokens expect correct tokens`() {
        // given:
        val assignment = "String reading = read();"
        val subject = arrayListOf<Token>(
                LexicalToken.KeyWord(value = "String", code = 3),
                LexicalToken.Identifier(value = "reading"),
                LexicalToken.KeyWord(value = "=", code = 4),
                LexicalToken.KeyWord(value = "read", code = 7),
                LexicalToken.KeyWord(value = "(", code = 10),
                LexicalToken.KeyWord(value = ")", code = 11),
                LexicalToken.KeyWord(value = ";", code = 5)
        )
        // when:
        val result = Tokens(Line(assignment, 0))
        // then:
        assertThat(result).containsAll(subject)
    }

    @Test
    fun `when converting print statement to tokens expect correct tokens`() {
        // given:
        val assignment = "String document = print(\"test\");"
        val subject = arrayListOf<Token>(
                LexicalToken.KeyWord(value = "String", code = 3),
                LexicalToken.Identifier(value = "document"),
                LexicalToken.KeyWord(value = "=", code = 4),
                LexicalToken.KeyWord(value = "print", code = 6),
                LexicalToken.KeyWord(value = "(", code = 10),
                LexicalToken.KeyWord(value = "\"", code = 27),
                LexicalToken.Constant(value = "test"),
                LexicalToken.KeyWord(value = "\"", code = 27),
                LexicalToken.KeyWord(value = ")", code = 11),
                LexicalToken.KeyWord(value = ";", code = 5)
        )
        // when:
        val result = Tokens(Line(assignment, 0))
        // then:
        assertThat(result).containsAll(subject)
    }

    @Test
    fun `when converting for statement to tokens expect correct tokens`() {
        // given:
        val assignment = "for(Int index: range(1:10)) { }"
        val subject = arrayListOf<Token>(
                LexicalToken.KeyWord(value = "for", code = 24),
                LexicalToken.KeyWord(value = "Int", code = 2),
                LexicalToken.Identifier(value = "index"),
                LexicalToken.KeyWord(value = ":", code = 26),
                LexicalToken.KeyWord(value = "range", code = 25),
                LexicalToken.KeyWord(value = "(", code = 10),
                LexicalToken.Constant(value = "1"),
                LexicalToken.KeyWord(value = ":", code = 26),
                LexicalToken.Constant(value = "10"),
                LexicalToken.KeyWord(value = ")", code = 11),
                LexicalToken.KeyWord(value = ")", code = 11),
                LexicalToken.KeyWord(value = "{", code = 12),
                LexicalToken.KeyWord(value = "}", code = 13)
        )
        // when:
        val result = Tokens(Line(assignment, 0))
        // then:
        assertThat(result).containsAll(subject)
    }

    @Test
    fun `when converting if statement to tokens expect correct tokens`() {
        // given:
        val assignment = "if(integer >= 3) {} else if (integer <= 2) {}  else {}"
        val subject = arrayListOf<Token>(
                LexicalToken.KeyWord(value = "if", code = 8),
                LexicalToken.KeyWord(value = "(", code = 10),
                LexicalToken.Identifier(value = "integer"),
                LexicalToken.KeyWord(value = ">=", code = 18),
                LexicalToken.Constant(value = "3"),
                LexicalToken.KeyWord(value = ")", code = 11),
                LexicalToken.KeyWord(value = "else", code = 9),
                LexicalToken.KeyWord(value = "if", code = 8),
                LexicalToken.KeyWord(value = "(", code = 10),
                LexicalToken.Identifier(value = "integer"),
                LexicalToken.KeyWord(value = "<=", code = 19),
                LexicalToken.Constant(value = "2"),
                LexicalToken.KeyWord(value = ")", code = 11)
        )
        // when:
        val result = Tokens(Line(assignment, 0))
        // then:
        assertThat(result).containsAll(subject)
    }

}