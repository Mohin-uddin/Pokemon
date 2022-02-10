package com.example.pokemon.di

import com.example.pokemon.data.remote.PokemonApi
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.repository.PokemonRepositoryImp
import com.example.pokemon.util.ConstValue.BASE_URL
import com.example.pokemon.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api : PokemonApi
    ):PokemonRepository = PokemonRepositoryImp(api)



    @Singleton
    @Provides
    fun providePokemonApi(): PokemonApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create()

    @Singleton
    @Provides
    fun provideDispatcher() : DispatcherProvider = object : DispatcherProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }

}