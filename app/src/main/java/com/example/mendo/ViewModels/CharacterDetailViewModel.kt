package com.example.mendo.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mendo.models.Character
import com.example.mendo.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailViewModel : ViewModel() {

    // Usamos ApiResult en lugar de Result para evitar el conflicto de nombres
    private val _characterDetail = MutableStateFlow<ApiResult<Character?>>(ApiResult.Loading)
    val characterDetail: StateFlow<ApiResult<Character?>> get() = _characterDetail

    fun getCharacterDetail(characterName: String) {
        _characterDetail.value = ApiResult.Loading
        viewModelScope.launch {
            // Realiza la llamada a la API para obtener los detalles del personaje por nombre
            ApiClient.api.getCharacterDetailByName(characterName).enqueue(object : Callback<Character> {
                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    if (response.isSuccessful) {
                        // Aquí estamos esperando un solo personaje, no una lista
                        _characterDetail.value = ApiResult.success(response.body())
                    } else {
                        _characterDetail.value =
                            ApiResult.error("No character found or error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    _characterDetail.value = ApiResult.error(t.message ?: "Unknown Error")
                    Log.e("CharacterDetailViewModel", "Error: ${t.message}")
                }
            })
        }
    }
}

// Usamos ApiResult para manejar los estados de la API
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()

    companion object {
        fun <T> success(data: T): ApiResult<T> = Success(data)
        fun error(message: String): ApiResult<Nothing> = Error(message)
        fun loading(): ApiResult<Nothing> = Loading
    }
}
