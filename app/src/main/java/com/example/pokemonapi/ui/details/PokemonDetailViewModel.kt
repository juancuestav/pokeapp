package com.example.pokemonapi.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapi.data.model.PokemonModel
import com.example.pokemonapi.domain.GetPokemonByIdUseCase
import com.example.pokemonapi.domain.GetPokemonImageFromPngOriginUseCase
import com.example.pokemonapi.domain.GetPokemonImageFromSvgOriginUseCase
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {
    val pokemonModel = MutableLiveData<PokemonModel>()
    val pokemonImageFromSvgOrigin = MutableLiveData<ByteArray>()
    val pokemonImageFromPngOrigin = MutableLiveData<ByteArray>()
    val isLoading = MutableLiveData<Boolean>()

    private val getPokemonImageFromSvgOriginUseCase = GetPokemonImageFromSvgOriginUseCase()
    private val getPokemonImageFromPngOriginUseCase = GetPokemonImageFromPngOriginUseCase()
    private val getPokemonByIdUseCase = GetPokemonByIdUseCase()

    fun getPokemonImageFromSvgOrigin(pokemon: PokemonModel) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getPokemonImageFromSvgOriginUseCase(pokemon)

            if (result != null) {
                pokemonImageFromSvgOrigin.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }

    fun getPokemonImageFromPngOrigin(pokemon: PokemonModel) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getPokemonImageFromPngOriginUseCase(pokemon)

            if (result != null) {
                pokemonImageFromPngOrigin.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }

    // Get pokemon by name
    fun getPokemonById(idPokemon: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getPokemonByIdUseCase(idPokemon)

            if (result != null) {
                pokemonModel.postValue(result[0]!!)
                isLoading.postValue(false)
            }
        }
    }
}