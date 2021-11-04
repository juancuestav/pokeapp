package com.example.pokemonapi.data.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("status") var status: String,
    @SerializedName("result") var result: List<PokemonModel>
)