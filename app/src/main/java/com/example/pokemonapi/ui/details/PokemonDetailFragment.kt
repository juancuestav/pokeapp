package com.example.pokemonapi.ui.details

import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.example.pokemonapi.databinding.PokemonDetailFragmentBinding
import com.pixplicity.sharp.Sharp
import com.example.pokemonapi.core.Extensions


class PokemonDetailFragment : Fragment() {

    /* companion object {
        fun newInstance() = PokemonDetailFragment()
    } */

    private lateinit var viewModel: PokemonDetailViewModel

    private var _binding: PokemonDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PokemonDetailViewModel::class.java)
        setObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PokemonDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PokemonDetailFragmentArgs by navArgs()
        val idPokemon = args.idPokemon
        viewModel.getPokemonById(idPokemon)
    }

    private fun setObservers() {
        viewModel.pokemonImageFromSvgOrigin.observe(this, {
            Sharp.loadInputStream(it.inputStream()).into(binding.ivPokemonDetail)
        })

        viewModel.pokemonImageFromPngOrigin.observe(this, {
            Palette.from(BitmapFactory.decodeStream(it.inputStream())).generate {palette ->
                val vibrant = palette?.vibrantSwatch
                val lightVibrant = palette?.lightVibrantSwatch
                if (vibrant != null && lightVibrant !== null) {
                    binding.tvNamePokemon.setTextColor(vibrant.titleTextColor)
                    binding.idTv.setTextColor(lightVibrant.rgb)
                    (binding.tvNamePokemon.background as GradientDrawable).setColor(lightVibrant.rgb);
                    binding.containerPokemonDetail.setBackgroundColor(vibrant.rgb)
                }
            }
        })

        viewModel.isLoading.observe(this, {
            binding.pbPokemonDetail.isVisible = it
        })

        viewModel.pokemonModel.observe(this, {
            viewModel.getPokemonImageFromSvgOrigin(it)
            viewModel.getPokemonImageFromPngOrigin(it)
            binding.idTv.text = Extensions.agregarCerosIzquierda(it.id, 3)
            binding.tvNamePokemon.text = it.name
            //println(it.types!![0].type.name)
        })
    }
}