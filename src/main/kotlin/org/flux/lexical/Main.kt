package org.flux.lexical

import com.xenomachina.argparser.ArgParser
import org.flux.lexical.form.InternalForm
import java.io.File

fun main(args: Array<String>) {
    ArgParser(args).parseInto(::FluxArgs).run {
        InternalForm(File(source)).forEach { println(it) }
    }
}

