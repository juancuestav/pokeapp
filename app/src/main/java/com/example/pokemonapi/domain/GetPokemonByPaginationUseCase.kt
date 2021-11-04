package com.example.pokemonapi.domain

import com.example.pokemonapi.data.model.PokemonModel
import com.example.pokemonapi.data.PokemonRepository

// Contains the repository
class GetPokemonByPaginationUseCase {
    private val repository = PokemonRepository()

    suspend operator fun invoke(offset: Int): List<PokemonModel> {
        return repository.getPokemonByPagination(offset)
    }
}