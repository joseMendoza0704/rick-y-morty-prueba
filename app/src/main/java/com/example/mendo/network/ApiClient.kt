package com.example.mendo.network

import com.example.mendo.models.Character
import com.example.mendo.models.CharacterList
import com.example.mendo.models.EpisodeResponse
import com.example.mendo.models.LocationList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("character/")
    fun searchCharactersByName(@Query("name") name: String): Call<CharacterList>



    @GET("character/{name}")
    fun getCharacterDetailByName(@Path("name") name: String): Call<Character>


// Cambiar a un solo Character
// Nueva llamada para buscar por nombre


    @GET("episode")
    fun getEpisodes(): Call<EpisodeResponse>

    @GET("location")
    fun getLocations(): Call<LocationList>

    @GET("character")
    fun getCharacters(): Call<CharacterList>  // Esta es la llamada que obtiene la lista de personajes

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"

        val api: ApiClient by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(ApiClient::class.java)
        }
    }
}
