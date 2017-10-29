package flux.domain

/**
 * @author Alexandru Stoica
 * @version 1.0
 */

@Suppress("unused")
class SymbolTable<K, V> {

    private class Symbol<out K, out V>(val key: K, val value: V)

    private val list: MutableList<Symbol<K, V>> = mutableListOf()

    fun put(key: K, value: V): V =
            list.add(findFitIndexFor(key, 0), Symbol(key, value)).let { value }

    fun getOrPut(key: K, valueGenerator: () -> V): V {
        return get(key) ?: put(key, valueGenerator())
    }

    fun get(key: K): V? = list.findLast { it.key == key }?.value

    fun isEmpty(): Boolean = list.isEmpty()

    fun size(): Int = list.size

    fun values(): List<V> = list.map { it.value }.toList()

    fun keys(): List<K> = list.map { it.key }.toList()

    fun forEach(function: (K, V) -> Unit) = list.forEach { function(it.key, it.value) }

    private fun findFitIndexFor(key: K, index: Int): Int =
            if (list.size == index || list[index].key.toString() > key.toString()) index
            else findFitIndexFor(key, index + 1)
}

