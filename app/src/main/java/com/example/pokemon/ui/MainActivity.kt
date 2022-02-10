package com.example.pokemon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.manager.ConnectivityMonitor
import com.example.pokemon.R
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.ui.adapter.PokemonMovesAdapter
import com.example.pokemon.ui.adapter.PokemonStatisticsAdapter
import com.example.pokemon.util.PokemonDetailsEvent
import com.example.pokemon.util.gone
import com.example.pokemon.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movesAdapter : PokemonMovesAdapter
    private lateinit var statAdapter : PokemonStatisticsAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterInitialize()

        if(viewModel.pokemonList.value.isEmpty()) {
            viewModel.getPokemonList()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.pokemonList.collect {
                getPokemonDetails()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.pokemonDetail.collect { event ->
                when(event){
                    is PokemonDetailsEvent.SelectedPokemon ->{
                        binding.tvPokemonName.text = event.pokemonMainData?.pokemonName
                        Glide.with(this@MainActivity)
                            .load(event.pokemonMainData?.pokemonImageUrl)
                            .placeholder(R.drawable.loader)
                            .into(binding.ivPokemonPic);
                    }
                    is PokemonDetailsEvent.PokemonDetail ->{
                        if(event.pokemonDetails?.moves?.size==0){
                            binding.tvHeadMove.gone()
                            binding.rvMoves.gone()
                        }
                        else
                        {
                            binding.tvHeadMove.show()
                            binding.rvMoves.show()
                        }
                        statAdapter.submitList(event.pokemonDetails?.stats)
                        movesAdapter.submitList(event.pokemonDetails?.moves)
                    }
                    is PokemonDetailsEvent.Failure -> {
                       Toast.makeText(this@MainActivity,event.error,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnRefresh.setOnClickListener {
            getPokemonDetails()
        }
    }

    private fun adapterInitialize() {
        movesAdapter = PokemonMovesAdapter()
        binding.rvMoves.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvMoves.adapter = movesAdapter

        statAdapter = PokemonStatisticsAdapter()
        binding.rvStatistics.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvStatistics.adapter = statAdapter
    }

    private fun getPokemonDetails(){
        if(viewModel.pokemonList.value.isNotEmpty()) {
            viewModel.randomNumberSelect()
            viewModel.getPokemonDetails()
        }
    }



}