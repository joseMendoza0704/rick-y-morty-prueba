// LocationListScreen.kt
package com.example.mendo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mendo.network.ApiClient
import com.example.mendo.models.Location
import com.example.mendo.models.LocationList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LocationListScreen() {
    var locations by remember { mutableStateOf<List<Location>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Llamada a la API para obtener ubicaciones
    LaunchedEffect(true) {
        ApiClient.api.getLocations().enqueue(object : Callback<LocationList> {
            override fun onResponse(call: Call<LocationList>, response: Response<LocationList>) {
                if (response.isSuccessful) {
                    locations = response.body()?.results ?: emptyList()
                } else {
                    errorMessage = "Error: ${response.code()}"
                }
                isLoading = false
            }

            override fun onFailure(call: Call<LocationList>, t: Throwable) {
                errorMessage = t.message
                isLoading = false
            }
        })
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary)
    } else if (errorMessage != null) {
        Text(text = "Error: $errorMessage", color = Color.Red, style = MaterialTheme.typography.bodyLarge)
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
            items(locations) { location ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(location.name, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        Text(location.type, style = MaterialTheme.typography.bodySmall, color = Color.White)
                        Text(location.dimension, style = MaterialTheme.typography.bodySmall, color = Color.White)
                    }
                }
            }
        }
    }
}
