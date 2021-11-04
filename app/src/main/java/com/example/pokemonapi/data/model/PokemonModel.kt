package com.example.pokemonapi.data.model

data class PokemonModel(
    val id: Int,
    val name: String,
    val image_svg: String,
    val image_png: String,
    val type: String,
    val height: Float,
    val weight: Float
)