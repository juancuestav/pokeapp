package com.example.pokemonapi.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapi.data.model.PokemonModel
import com.example.pokemonapi.databinding.MainFragmentBinding

class MainFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    /*companion object {
        fun newInstance() = MainFragment()
    }*/

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: PokemonAdapter
    private val pokemonList = mutableListOf<PokemonModel>()
    private var offset: Int = 0
    private var pagingIsEnabled: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getPokemonByPagination(offset)
        setObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        setListeners()
    }

    private fun setObservers() {
        viewModel.pokemonList.observe(this, Observer {
            pokemonList.addAll(it)
            adapter.notifyItemRangeInserted(offset + 1, 20);
            pagingIsEnabled = true
            hideKeyboard()
        })

        viewModel.isLoading.observe(this, {
            binding.pbPokemon.isVisible = it
        })
    }

    private fun setListeners() {
        binding.querySV.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            pokemonList.clear()
            viewModel.searchByName(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        /*if (newText.isNullOrEmpty()) {
            offset = 0
            pokemonList.clear()
            viewModel.onCreate(offset)
            return true
        }*/
        return false
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        adapter = PokemonAdapter(pokemonList, this.requireContext())
        binding.pokemonRV.layoutManager = layoutManager
        binding.pokemonRV.adapter = adapter
        binding.pokemonRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount: Int = layoutManager.childCount
                    val totalItemCount: Int = layoutManager.itemCount
                    val pastVisibleItems: Int = layoutManager.findFirstVisibleItemPosition()
                    if (pagingIsEnabled) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            pagingIsEnabled = false
                            offset += 20
                            viewModel.getPokemonByPagination(offset)
                        }
                    }
                }
            }
        })
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.mainFragment.windowToken, 0)
    }
}