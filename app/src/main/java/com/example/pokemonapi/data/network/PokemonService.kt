package com.example.pokemonapi.data.network

import android.util.Log
import com.example.pokemonapi.data.model.PokemonModel
import com.example.pokemonapi.core.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// SOLID principle
// It is called by the repository when you want to obtain the data from the Internet
class PokemonService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getPokemonByPagination(offset: Int): List<PokemonModel> {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(PokemonApiClient::class.java).getListPokemon("pokemon", 20, offset)
            response.body()?.result ?: emptyList()
        }
    }

    suspend fun getPokemonByName(pokemonName: String): PokemonModel {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(PokemonApiClient::class.java).getPokemonByName("pokemon", pokemonName)
            val pokemon = response.body()?.result?.get(0)
            val id = pokemon?.id ?: 1
            val name = pokemon?.name ?: ""
            val imageSvg = pokemon?.image_svg ?: ""
            val imagePng = pokemon?.image_png ?: ""
            val type = pokemon?.type ?: ""
            val height = pokemon?.height ?: 0.0f
            val weight = pokemon?.weight ?: 0.0f
            PokemonModel(id, name, imageSvg, imagePng, type, height, weight)
        }
    }

    suspend fun getPokemonById(idPokemon: Int): PokemonModel {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(PokemonApiClient::class.java).getPokemonById("pokemon", idPokemon)
            val pokemon = response.body()?.result?.get(0)
            val id = pokemon?.id ?: 1
            val name = pokemon?.name ?: ""
            val imageSvg = pokemon?.image_svg ?: ""
            val imagePng = pokemon?.image_png ?: ""
            val type = pokemon?.type ?: ""
            val height = pokemon?.height ?: 0.0f
            val weight = pokemon?.weight ?: 0.0f
            PokemonModel(id, name, imageSvg, imagePng, type, height, weight)
        }
    }

    suspend fun getPokemonImageFromSvgOrigin(pokemon: PokemonModel): ByteArray? {
        return withContext(Dispatchers.IO) {
            val url = pokemon.image_svg
            Log.d("POKEDEX", "Consultando imagen SVG")
            Log.d("POKEDEX", url)
            val response = retrofit.create(PokemonApiClient::class.java).getPokemonImage(url)
            response.body()?.bytes() ?: null
        }
    }

    suspend fun getPokemonImageFromPngOrigin(pokemon: PokemonModel): ByteArray? {
        return withContext(Dispatchers.IO) {
            val url = pokemon.image_png
            Log.d("POKEDEX", url)
            val response = retrofit.create(PokemonApiClient::class.java).getPokemonImage(url)
            response.body()?.bytes() ?: null
        }
    }
}