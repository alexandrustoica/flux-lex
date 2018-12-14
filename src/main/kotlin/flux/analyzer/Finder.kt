package flux.analyzer


class Finder {

    fun findAllIn(source: String, basedOnRegex: Regex): List<String> =
            basedOnRegex.findAll(source)
                    .map { it.groupValues }
                    .map { it.first() }
                    .toList()

}
