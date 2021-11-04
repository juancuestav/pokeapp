package com.example.pokemonapi.data.network

import com.example.pokemonapi.data.model.PokemonResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

// Contains methods that access the API
interface PokemonApiClient {
    @GET
    suspend fun getListPokemon(
        @Url url: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonResponse>

    @GET
    suspend fun getPokemonById(
        @Url url: String,
        @Query("id") id: Int
    ): Response<PokemonResponse>

    @GET
    suspend fun getPokemonByName(
        @Url url: String,
        @Query("name") name: String
    ): Response<PokemonResponse>

    @GET
    suspend fun getPokemonImage(
        @Url url: String,
    ): Response<ResponseBody>
}