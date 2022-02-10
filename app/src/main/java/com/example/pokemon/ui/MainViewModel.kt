package com.example.pokemon.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.models.PokemonMainData
import com.example.pokemon.repository.PokemonRepositoryImp
import com.example.pokemon.util.ConstValue.PAGE_SIZE
import com.example.pokemon.util.DispatcherProvider
import com.example.pokemon.util.PokemonDetailsEvent
import com.example.pokemon.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pokemonRepoImp: PokemonRepositoryImp,
    private val dispatcher: DispatcherProvider
): ViewModel() {

    private var selectedPokemon= MutableStateFlow<PokemonMainData?>(null)
    private var _pokemonList = MutableStateFlow<List<PokemonMainData>>(listOf())
    val pokemonList : StateFlow<List<PokemonMainData>> = _pokemonList
    var loadError = MutableStateFlow("")
    var isLoading =  MutableStateFlow(false)
    var selectedPokemonName = MutableStateFlow("")
    private val _pokemonDetail = MutableStateFlow<PokemonDetailsEvent>(PokemonDetailsEvent.Empty)
    val pokemonDetail: StateFlow<PokemonDetailsEvent> = _pokemonDetail




    fun getPokemonList(){
        isLoading.value=true
        viewModelScope.launch(dispatcher.io) {
            val result = pokemonRepoImp.getPokemonList(PAGE_SIZE,0)

            when(result){
                is Resource.Success -> {
                   val pokemonEntry = result.data!!.results.mapIndexed { index, entry ->
                       val number = if(entry.url.endsWith("/")){
                           entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                       }else{
                           entry.url.takeLastWhile { it.isDigit() }
                       }
                       val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                       PokemonMainData(entry.name.capitalize(),url)
                   }
                    loadError.value = ""
                    isLoading.value = false
                    _pokemonList.value += pokemonEntry
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value=false
                }
            }
        }
    }


    fun getPokemonDetails(){

        Log.e("CheckingRandom", "randomNumberSelect lower: ${selectedPokemonName.value.lowercase()} " )
        viewModelScope.launch(dispatcher.io) {

            val result = pokemonRepoImp.getPokemonDetails(selectedPokemonName.value.lowercase())

            when(result){

                is Resource.Success -> {
                    _pokemonDetail.value = PokemonDetailsEvent.PokemonDetail(result.data)
                }

                is Resource.Error -> {
                    _pokemonDetail.value = PokemonDetailsEvent.Failure(result.message?:"Something is wrong")
                }
            }
        }
    }



    fun randomNumberSelect(){
        val randomizer = Random()
        val pokemonListSize = _pokemonList.value.size
        val randomIndex = randomizer.nextInt( pokemonListSize)


        selectedPokemonName.value =
            pokemonList.value[randomIndex].pokemonName
        selectedPokemon.value = pokemonList.value[randomIndex]
        Log.e("CheckingRandom", "randomNumberSelect1: ${selectedPokemonName.value} " )
        _pokemonDetail.value = PokemonDetailsEvent.SelectedPokemon(selectedPokemon.value)

    }

    fun String.capitalize(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }

}