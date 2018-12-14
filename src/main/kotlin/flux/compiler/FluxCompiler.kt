package flux.compiler

import java.io.File


class FluxCompiler: Compiler {
    override fun compile(source: File): Compiler = this
}