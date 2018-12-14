package flux.compiler

import java.io.File


interface Compiler {
    fun compile(source: File): Compiler
}