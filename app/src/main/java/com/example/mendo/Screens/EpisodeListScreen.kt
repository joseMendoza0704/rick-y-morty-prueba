package com.example.mendo.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mendo.ViewModels.EpisodeListViewModel
import com.example.mendo.ViewModels.EpisodeResult
import com.example.mendo.models.Episode

@Composable
fun EpisodeListScreen() {
    // Obtener el ViewModel
    val viewModel: EpisodeListViewModel = viewModel()
    val episodesState by viewModel.episodes.collectAsState()

    // Mostrar el indicador de carga o error
    when (episodesState) {
        is EpisodeResult.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is EpisodeResult.Error -> {
            Text("Error: ${(episodesState as EpisodeResult.Error).message}", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        is EpisodeResult.Success -> {
            val episodes = (episodesState as EpisodeResult.Success<List<Episode>>).data
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1e2838)),
                    contentPadding = PaddingValues(16.dp)) {
                items(episodes) { episode ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF38761D)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(episode.name, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                            Text(episode.air_date, style = MaterialTheme.typography.bodySmall, color = Color.White)
                        }
                    }
                }
            }
        }

        else -> {}
    }
}
