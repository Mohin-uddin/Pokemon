package com.example.pokemon.data.response.pokemon_details

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)