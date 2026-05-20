package com.jcacosta.pokespace.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val id: Int,
    val name: String,
    val sprites: SpritesDto,
    val types: List<TypeSlotDto>,
    val height: Int,
    val weight: Int,
    val stats: List<StatSlotDto>,
    val abilities: List<AbilitySlotDto>
)

@Serializable
data class SpritesDto(@SerialName("front_default") val frontDefault: String?)

@Serializable
data class NamedResourceDto(val name: String)

@Serializable
data class TypeSlotDto(val type: NamedResourceDto)

@Serializable
data class StatSlotDto(
    @SerialName("base_stat") val baseStat: Int,
    val stat: NamedResourceDto
)

@Serializable
data class AbilitySlotDto(val ability: NamedResourceDto)
