package com.example.pokemonapi.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://ecuadteam-pokeapi.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}