package org.flux.lexical
import com.xenomachina.argparser.ArgParser

fun main(args: Array<String>) {
    ArgParser(args).parseInto(::FluxArgs).run {
//        val analyzer = LexicalAnalyzer(File(source)).analyze()
//        analyzer.errors.forEach { println(it.message) }
//        analyzer.internalForm.forEach { println(it) }
    }
}

