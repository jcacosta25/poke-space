package com.jcacosta.pokespace.data.repository

import com.jcacosta.pokespace.data.api.dto.PokemonResponse
import com.jcacosta.pokespace.domain.model.Pokemon
import com.jcacosta.pokespace.domain.repository.PokemonRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class PokemonRepositoryImpl(private val httpClient: HttpClient) : PokemonRepository {

    override suspend fun getPokemonDetails(name: String): Pokemon {
        val response: PokemonResponse = httpClient
            .get("https://pokeapi.co/api/v2/pokemon/$name")
            .body()

        return Pokemon(
            id = response.id,
            name = response.name.replaceFirstChar { it.uppercase() },
            imageUrl = response.sprites.frontDefault.orEmpty(),
            types = response.types.map { it.type.name },
            height = response.height,
            weight = response.weight,
            stats = response.stats.associate { it.stat.name to it.baseStat },
            abilities = response.abilities.map { it.ability.name }
        )
    }
}