package org.flux.lexical

internal class AtomsFromLine {
    companion object : (String) -> List<String> {
        override fun invoke(line: String): List<String> {
            return AtomRegex.REGEX_TOKENS.toRegex().findAll(line)
                    .map { it.groupValues }
                    .map { it.first() }
                    .toList()
        }
    }
}