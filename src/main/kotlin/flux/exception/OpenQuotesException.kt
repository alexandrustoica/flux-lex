package flux.exception

import flux.domain.Location

/**
 * @author Alexandru Stoica
 * @version 1.0
 */

class OpenQuotesException(location: Location):
        Exception("ERROR! The quotes at location ${location.line}::${location.index} are not closed!")