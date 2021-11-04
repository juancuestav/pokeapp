package com.example.pokemonapi.domain

import com.example.pokemonapi.data.PokemonRepository
import com.example.pokemonapi.data.model.PokemonModel

// Contains the repository
class GetPokemonImageFromSvgOriginUseCase {
    private val repository = PokemonRepository()

    suspend operator fun invoke(pokemon: PokemonModel): ByteArray? = repository.getPokemonImageFromSvgOrigin(pokemon)
}