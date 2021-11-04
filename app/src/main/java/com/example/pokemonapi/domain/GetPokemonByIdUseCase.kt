package com.example.pokemonapi.domain

import com.example.pokemonapi.data.model.PokemonModel
import com.example.pokemonapi.data.PokemonRepository

// Interact with the data repository
class GetPokemonByIdUseCase {
    private val repository = PokemonRepository()

    suspend operator fun invoke(idPokemon: Int): List<PokemonModel>? {
        val pokemonList = ArrayList<PokemonModel>()
        pokemonList.add(repository.getPokemonById(idPokemon))
        return pokemonList
    }
}