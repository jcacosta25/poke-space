package com.jcacosta.pokespace.domain.usecase

import com.jcacosta.pokespace.domain.model.Pokemon
import com.jcacosta.pokespace.domain.repository.PokemonRepository

class GetPokemonDetailsUseCase(private val repository: PokemonRepository) {

    @Throws(Exception::class)
    suspend fun execute(name: String): Pokemon {
        require(name.isNotBlank()) { "El nombre del Pokémon no puede estar vacío" }

        return repository.getPokemonDetails(name.lowercase().trim())
    }
}