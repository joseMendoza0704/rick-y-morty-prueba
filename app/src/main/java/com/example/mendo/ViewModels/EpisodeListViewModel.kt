package com.example.mendo.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mendo.models.Episode
import com.example.mendo.models.EpisodeResponse
import com.example.mendo.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisodeListViewModel : ViewModel() {

    private val _episodes = MutableStateFlow<EpisodeResult<List<Episode>>>(EpisodeResult.Loading)
    val episodes: StateFlow<EpisodeResult<List<Episode>>> get() = _episodes

    init {
        fetchEpisodes()
    }

    private fun fetchEpisodes() {
        _episodes.value = EpisodeResult.Loading
        ApiClient.api.getEpisodes().enqueue(object : Callback<EpisodeResponse> {
            override fun onResponse(call: Call<EpisodeResponse>, response: Response<EpisodeResponse>) {
                if (response.isSuccessful) {
                    val episodes = response.body()?.results ?: emptyList()
                    _episodes.value = EpisodeResult.Success(episodes)
                } else {
                    _episodes.value = EpisodeResult.Error("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EpisodeResponse>, t: Throwable) {
                _episodes.value = EpisodeResult.Error("Error: ${t.message}")
                Log.e("EpisodeListViewModel", "Error: ${t.message}")
            }
        })
    }
}

sealed class EpisodeResult<out T> {
    data class Success<out T>(val data: T) : EpisodeResult<T>()
    data class Error(val message: String) : EpisodeResult<Nothing>()
    object Loading : EpisodeResult<Nothing>()
}
