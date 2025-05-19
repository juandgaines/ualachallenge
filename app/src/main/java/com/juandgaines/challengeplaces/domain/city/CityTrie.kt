package com.juandgaines.challengeplaces.domain.city

class CityTrie {
    private val root = TrieNode()

    private var lastPrefix: String = ""

    fun setLastPrefix(prefix: String) {
        lastPrefix = prefix
    }

    fun shouldRebuildFor(prefix: String): Boolean {
        if (lastPrefix.isEmpty()) return true
        return !prefix.startsWith(lastPrefix)
    }

    fun clear() {
        lastPrefix = ""
        root.children.clear()
        root.cities.clear()
    }

    fun insert(city: City) {
        var node = root
        for (char in city.name.lowercase()) {
            node = node.children.computeIfAbsent(char) { TrieNode() }
            node.cities.add(city)
        }
        node.isEnd = true
    }

    fun searchByPrefix(prefix: String): List<City> {

        var node = root
        for (char in prefix.lowercase()) {
            node = node.children[char] ?: return emptyList()
        }
        return node.cities
    }
}