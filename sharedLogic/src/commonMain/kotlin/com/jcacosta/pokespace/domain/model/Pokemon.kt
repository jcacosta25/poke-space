package com.jcacosta.pokespace.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val height: Int,
    val weight: Int,
    val stats: Map<String, Int>,
    val abilities: List<String>
)