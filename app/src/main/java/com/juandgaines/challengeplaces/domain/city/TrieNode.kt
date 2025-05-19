package com.juandgaines.challengeplaces.domain.city

class TrieNode {
    val children = mutableMapOf<Char, TrieNode>()
    val cities = mutableListOf<City>()
    var isEnd = false
}