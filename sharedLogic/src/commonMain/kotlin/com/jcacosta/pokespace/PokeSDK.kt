package com.jcacosta.pokespace

import com.jcacosta.pokespace.data.repository.PokemonRepositoryImpl
import com.jcacosta.pokespace.domain.repository.PokemonRepository
import com.jcacosta.pokespace.domain.usecase.GetPokemonDetailsUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class PokeSDK private constructor(
    private val httpClient: HttpClient,
    private val repository: PokemonRepository
) {
    val getPokemonDetailsUseCase: GetPokemonDetailsUseCase by lazy {
        GetPokemonDetailsUseCase(repository)
    }

    /**
     * Constructor público oficial para Producción.
     * Configura internamente Ktor con las reglas de parseo JSON necesarias.
     */
    constructor() : this(
        httpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    )

    private constructor(httpClient: HttpClient) : this(
        httpClient = httpClient,
        repository = PokemonRepositoryImpl(httpClient)
    )
}