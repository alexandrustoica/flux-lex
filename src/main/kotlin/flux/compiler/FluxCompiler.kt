package flux.compiler

import java.io.File

/**
 * @author Alexandru Stoica
 * @version 1.0
 */

class FluxCompiler: Compiler {
    override fun compile(source: File): Compiler = this
}