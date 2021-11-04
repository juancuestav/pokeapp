package com.example.pokemonapi.data

import com.example.pokemonapi.data.model.PokemonModel
import com.example.pokemonapi.data.model.PokemonProvider
import com.example.pokemonapi.data.network.PokemonService

// Decide whether to get the data from the Internet or from another source
// It is recommended to first check if the data was cached but to query the API
class PokemonRepository {
    private val pokemonService = PokemonService()

    suspend fun getPokemonByPagination(offset: Int): List<PokemonModel> {
        val response = pokemonService.getPokemonByPagination(offset)     // Request data from the server
        PokemonProvider.pokemonList = response                           // Stores the data obtained in memory
        return response
    }

    suspend fun getPokemonByName(name: String): PokemonModel = pokemonService.getPokemonByName(name)

    suspend fun getPokemonById(id: Int): PokemonModel = pokemonService.getPokemonById(id)

    suspend fun getPokemonImageFromSvgOrigin(pokemon: PokemonModel): ByteArray? {
        val lastSearchedPokemon: Int = PokemonProvider.lastIdSvg
        if (pokemon.id != lastSearchedPokemon) {
            PokemonProvider.lastIdSvg = pokemon.id
            PokemonProvider.lastByteArraySvg = pokemonService.getPokemonImageFromSvgOrigin(pokemon)
        }
        return PokemonProvider.lastByteArraySvg
    }

    suspend fun getPokemonImageFromPngOrigin(pokemon: PokemonModel): ByteArray? {
        val lastSearchedPokemon: Int = PokemonProvider.lastIdPng
        if (pokemon.id != lastSearchedPokemon) {
            PokemonProvider.lastIdPng = pokemon.id
            PokemonProvider.lastByteArrayPng = pokemonService.getPokemonImageFromPngOrigin(pokemon)
        }
        return PokemonProvider.lastByteArrayPng
    }
}