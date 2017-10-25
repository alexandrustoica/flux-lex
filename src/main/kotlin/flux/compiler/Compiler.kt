package flux.compiler

import java.io.File

/**
 * @author Alexandru Stoica
 * @version 1.0
 */

interface Compiler {
    fun compile(source: File): Compiler
}