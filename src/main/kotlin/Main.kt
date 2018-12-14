
import flux.analyzer.LexicalAnalyzer
import java.io.File



fun main(args: Array<String>) {
    val analyzer = LexicalAnalyzer(File("source.flux")).analyze()
    analyzer.validator.accumulator.forEach { println(it.message)}
    analyzer.internalForm.forEach { println("${it.first} | ${it.second}") }
}