package com.example.pokemonapi.domain

import com.example.pokemonapi.data.model.PokemonModel
import com.example.pokemonapi.data.PokemonRepository

// Contiene el repositorio
class GetPokemonByNameUseCase {
    private val repository = PokemonRepository()

    suspend operator fun invoke(name: String): List<PokemonModel>? {
        val pokemonList = ArrayList<PokemonModel>()
        pokemonList.add(repository.getPokemonByName(name))
        return pokemonList
    }
}