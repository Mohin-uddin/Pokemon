package com.example.pokemon.repository

import com.example.pokemon.data.response.pokemon_details.PokemonDetailsResponse
import com.example.pokemon.data.response.pokemon_lists.PokemonList
import com.example.pokemon.util.Resource

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int) : Resource<PokemonList>
    suspend fun getPokemonDetails(pokemonName: String) : Resource<PokemonDetailsResponse>
}