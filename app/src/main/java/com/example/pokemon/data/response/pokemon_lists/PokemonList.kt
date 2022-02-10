package com.example.pokemon.data.response.pokemon_lists

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)