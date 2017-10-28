package flux.exception

import flux.domain.Location

/**
 * @author Alexandru Stoica
 * @version 1.0
 */

class PropertyNameException(name: String, location: Location):
        Exception("ERROR! The element $name at location ${location.line}::${location.index} has an invalid name!")