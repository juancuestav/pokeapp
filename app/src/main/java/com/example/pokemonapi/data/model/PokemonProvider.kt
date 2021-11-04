package com.example.pokemonapi.data.model

// It stores the data obtained from the Internet in RAM.
class PokemonProvider {
    companion object {
        var pokemonList: List<PokemonModel> = emptyList()
        var pokemon: PokemonModel? = null
        var lastByteArraySvg: ByteArray? = null
        var lastByteArrayPng: ByteArray? = null
        var lastIdSvg: Int = 0
        var lastIdPng: Int = 0
    }
}