package com.juandgaines.challengeplaces.domain.city

class CityTrie {
    private val root = TrieNode()

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