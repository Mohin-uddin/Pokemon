package com.example.pokemon.repository

import com.example.pokemon.data.remote.PokemonApi
import com.example.pokemon.data.response.pokemon_details.PokemonDetailsResponse
import com.example.pokemon.data.response.pokemon_lists.PokemonList
import com.example.pokemon.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

class PokemonRepositoryImp @Inject constructor(
   private val api: PokemonApi
): PokemonRepository{

    override suspend fun getPokemonList(limit: Int, offset: Int) : Resource<PokemonList>{
        val response = try {
            api.getPokemonList(limit,offset)
        }catch (error: Exception){
            return Resource.Error("Something is wrong for this Api")
        }
        return Resource.Success(response)
    }

    override suspend fun getPokemonDetails(pokemonName: String) : Resource<PokemonDetailsResponse>{
        val response = try {
            api.getPokemonDetails(pokemonName)
        }catch (error: Exception){
            return Resource.Error("Something is wrong for this Api")
        }
        return Resource.Success(response)
    }

}