package com.jcacosta.pokespace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcacosta.pokespace.domain.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokedexViewModel(private val pokedex: PokeSDK) : ViewModel() {

    private val _uiState = MutableStateFlow<Pokemon?>(null)
    val uiState: StateFlow<Pokemon?> = _uiState

    fun findPokemon(name: String) {
        viewModelScope.launch {
            try {
                val result = pokedex.getPokemonDetailsUseCase.execute(name)
                _uiState.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}