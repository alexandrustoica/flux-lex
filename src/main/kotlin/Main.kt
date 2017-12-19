
import flux.analyzer.LexicalAnalyzer
import java.io.File

/**
 * @author Alexandru Stoica
 * @version 1.0
 */

fun main(args: Array<String>) {
    val analyzer = LexicalAnalyzer(File("source.flux")).analyze()
    // analyzer.symbolTable.forEach { key, value -> println("$key || $value") }
    analyzer.validator.accumulator.forEach { println(it.message)}
    analyzer.internalForm.forEach { println("${it.first} | ${it.second}") }
}