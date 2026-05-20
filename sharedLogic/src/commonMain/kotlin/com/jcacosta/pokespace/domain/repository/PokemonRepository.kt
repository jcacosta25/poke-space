package com.jcacosta.pokespace.domain.repository

import com.jcacosta.pokespace.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonDetails(name: String): Pokemon
}