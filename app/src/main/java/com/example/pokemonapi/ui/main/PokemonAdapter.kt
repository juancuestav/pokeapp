package com.example.pokemonapi.ui.main

import android.content.Context
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapi.data.model.PokemonModel
import com.example.pokemonapi.R
import com.example.pokemonapi.core.Extensions
import com.example.pokemonapi.databinding.PokemonRvadapterBinding
import com.squareup.picasso.Picasso

class PokemonAdapter(private val list: List<PokemonModel>, private val ctx: Context) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(R.layout.pokemon_rvadapter, parent, false),
            ctx
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(list[position])

        val pokemon = list[position]
        holder.render(pokemon)
        //holder.itemView.setOnClickListener { listener(poke) }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(private val view: View, val ctx: Context) :
        RecyclerView.ViewHolder(view), View.OnTouchListener {
        val binding = PokemonRvadapterBinding.bind(view)
        var idPokemon = 1
        private var viewtouch: View? = null
        private var gestureDetector: GestureDetectorCompat? = null

        fun render(pokemon: PokemonModel) {
            setupGesture()
            idPokemon = pokemon.id
            binding.tvName.text = pokemon.name
            binding.tvId.text = Extensions.agregarCerosIzquierda(pokemon.id, 3)
            Picasso.get().load("https://ecuadteam-pokeapi.000webhostapp.com/" + pokemon.image_png).into(binding.ivPokemon)
            binding.containerPokemon.setOnTouchListener(this)
        }

        override fun onTouch(view: View, motionEvent: MotionEvent?): Boolean {
            viewtouch = view
            gestureDetector?.onTouchEvent(motionEvent)
            return true
        }

        private fun setupGesture() {
            gestureDetector =
                GestureDetectorCompat(itemView.context, object : SimpleOnGestureListener() {
                    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                        val actionMainToPokemonDetail =
                            MainFragmentDirections.actionMainFragmentToPokemonDetailFragment(idPokemon)
                        Navigation.findNavController(itemView).navigate(actionMainToPokemonDetail)
                        return super.onSingleTapConfirmed(e)
                    }

                    override fun onDown(e: MotionEvent): Boolean {
                        //val anim = AnimationUtils.loadAnimation(itemView.context, R.anim.touchitem)
                        //itemView.startAnimation(anim)
                        return super.onDown(e)
                    }
                })
        }
    }
}