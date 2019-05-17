package org.flux.application

import com.xenomachina.argparser.ArgParser

internal class FluxArgs(parser: ArgParser) {
    val source: String by
    parser.positional("SOURCE", help = "source filename")
}