import flux.analyzer.LexicalAnalyzer
import java.io.File

/**
 * @author Alexandru Stoica
 * @version 1.0
 */

fun main(args: Array<String>) {
    val analyzer = LexicalAnalyzer(File("source.flux")).analyze()
    analyzer.internalForm.forEach { element -> println("${element.first} | ${element.second}") }
}