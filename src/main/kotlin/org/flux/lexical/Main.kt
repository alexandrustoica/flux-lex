package org.flux.lexical

import com.xenomachina.argparser.ArgParser
import java.io.File

fun main(args: Array<String>) {
    ArgParser(args).parseInto(::FluxArgs).run {
        InternalForm(File(source)).forEach { println(it) }
    }
}

