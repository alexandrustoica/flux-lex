package org.flux.lexical


internal class OpenQuotesException(location: Location):
        Exception("ERROR! The quotes at location " +
                "${location.line}::${location.index} are not closed!")
