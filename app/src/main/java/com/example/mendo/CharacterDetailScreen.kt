package com.example.mendo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CharacterDetailScreen(characterId: Int) {
    val viewModel: CharacterDetailViewModel = viewModel()

    // Usar collectAsState para observar el estado del LiveData
    val characterDetail by viewModel.characterDetail.collectAsState(ApiResult.Loading)

    // Llamada al ViewModel para obtener los datos
    LaunchedEffect(characterId) {
        // Convertimos el ID de personaje a String
        viewModel.getCharacterDetail(characterId.toString())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Añadimos padding general
        contentAlignment = Alignment.Center // Centramos todo el contenido vertical y horizontalmente
    ) {
        when (val result = characterDetail) {
            is ApiResult.Loading -> {
                // Mostrar el indicador de carga mientras obtenemos los datos
                CircularProgressIndicator()
            }
            is ApiResult.Success -> {
                val character = result.data
                character?.let {
                    // Card con sombra para mostrar los detalles del personaje
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(16.dp), // Espacio alrededor de la Card
                       // Elevación de la Card para sombra
                        shape = MaterialTheme.shapes.medium // Forma de la Card
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally // Centrar el contenido horizontalmente
                        ) {
                            // Imagen del personaje
                            Image(
                                painter = rememberImagePainter(it.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp) // Tamaño de la imagen
                                    .clip(CircleShape) // Imagen circular
                                    .align(Alignment.CenterHorizontally) // Centrar la imagen
                            )

                            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre la imagen y la información

                            // Información del personaje
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Species: ${it.species}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Status: ${it.status}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Gender: ${it.gender}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                }
            }
            is ApiResult.Error -> {
                // Mostrar el mensaje de error si la llamada a la API falla
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${result.message}", color = Color.Red)
                }
            }
        }
    }
}
