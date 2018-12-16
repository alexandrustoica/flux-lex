import com.xenomachina.argparser.ArgParser
import flux.analyzer.LexicalAnalyzer
import java.io.File

class FluxArgs(parser: ArgParser) {
    val source by parser.positional("SOURCE", help = "source filename")
}

fun main(args: Array<String>) {
    ArgParser(args).parseInto(::FluxArgs).run {
        val analyzer = LexicalAnalyzer(File(source)).analyze()
        analyzer.validator.accumulator.forEach { println(it.message) }
        analyzer.internalForm.forEach { println("${it.first} | ${it.second}") }
    }
}