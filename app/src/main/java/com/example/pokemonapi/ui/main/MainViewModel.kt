package com.example.pokemonapi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapi.data.model.PokemonModel
import com.example.pokemonapi.domain.GetPokemonByNameUseCase
import com.example.pokemonapi.domain.GetPokemonByPaginationUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

// Contains use cases
class MainViewModel : ViewModel() {
    val pokemonList = MutableLiveData<List<PokemonModel>>()
    val isLoading = MutableLiveData<Boolean>()

    private val getPokemonByPaginationUseCase = GetPokemonByPaginationUseCase()
    private val getPokemonByNameUseCase = GetPokemonByNameUseCase()

    fun getPokemonByPagination(offset: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val result = getPokemonByPaginationUseCase(offset)

                if (!result.isNullOrEmpty()) {
                    pokemonList.postValue(result!!)
                    print(pokemonList)
                    isLoading.postValue(false)
                }
            } catch (error: Exception) {
                println(error.message)
            }
        }
    }

    // Get pokemon by name
    fun searchByName(name: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getPokemonByNameUseCase(name)
            if (result != null) {
                pokemonList.postValue(result!!)
                isLoading.postValue(false)
            }
        }
    }

    // new
    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val movies: List<PokemonModel>) : UiModel()
        data class Navigation(val movie: PokemonModel) : UiModel()
    }

    fun onPokemonClicked(pokemon: PokemonModel) {
        _model.value = UiModel.Navigation(pokemon)
    }
}