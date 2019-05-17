package org.flux.lexical

internal class AtomRegex {
    companion object {
        private const val REGEX_RELATION = "!=|==|>=|<=|<|>"
        private const val REGEX_CONST_STRING = "\"[^\"]+\""
        private const val REGEX_SPECIAL_OPERATORS = "->"
        private const val REGEX_MATH_OPERATORS = "\\+|-|/|\\*|%"
        private const val REGEX_SEPARATORS = "[ (){}:;\",\\t]"
        private const val REGEX_ATTRIBUTION = "="
        private const val REGEX_OPERATORS =
                "$REGEX_SPECIAL_OPERATORS|$REGEX_RELATION|$REGEX_MATH_OPERATORS"
        private const val REGEX_NUMBERS = "\\d+"
        const val REGEX_TOKENS =
                "$REGEX_CONST_STRING|(\\w+)|$REGEX_OPERATORS|" +
                        "$REGEX_ATTRIBUTION|$REGEX_SEPARATORS|$REGEX_NUMBERS"
    }
}