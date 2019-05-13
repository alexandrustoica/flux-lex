package org.flux.application

import com.xenomachina.argparser.ArgParser

class FluxArgs(parser: ArgParser) {
    val source by parser.positional("SOURCE", help = "source filename")
}