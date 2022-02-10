package com.example.pokemon.data.remote

import com.example.pokemon.data.response.pokemon_details.PokemonDetailsResponse
import com.example.pokemon.data.response.pokemon_lists.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonDetails(
        @Path("pokemonName") pokemonName: String
    ): PokemonDetailsResponse
}