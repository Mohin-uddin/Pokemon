package com.example.pokemon.util

import com.example.pokemon.data.models.PokemonMainData
import com.example.pokemon.data.response.pokemon_details.Move
import com.example.pokemon.data.response.pokemon_details.PokemonDetailsResponse
import com.example.pokemon.data.response.pokemon_details.Stat

sealed class PokemonDetailsEvent {
    class PokemonDetail(val pokemonDetails: PokemonDetailsResponse?) : PokemonDetailsEvent()
    class SelectedPokemon(val pokemonMainData: PokemonMainData?) : PokemonDetailsEvent()
    class Failure(val error: String) : PokemonDetailsEvent()
    object Empty : PokemonDetailsEvent()
}