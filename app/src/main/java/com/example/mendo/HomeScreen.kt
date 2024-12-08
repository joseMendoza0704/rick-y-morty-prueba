package com.example.mendo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun HomeScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla de inicio
        item {
            Text(
                "Inicio",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }

        // Cards para opciones
        item {
            HomeOptionCard(
                imageUrl = "https://sm.ign.com/ign_latam/image/t/the-top-10/the-top-100-best-tv-shows-of-all-time_em2k.jpg",
                title = "Ver personajes",
                onClick = { navController.navigate("characterList") }
            )
        }

        item {
            HomeOptionCard(
                imageUrl = "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/media/image/2019/05/rick-morty-temporada-4-llegara-noviembre.jpg?tf=3840x",
                title = "Ver episodios",
                onClick = { navController.navigate("episodeList") }
            )
        }

        item {
            HomeOptionCard(
                imageUrl = "https://static.wikia.nocookie.net/rick-y-morty-espanol/images/0/0c/T1E03_-_Parque_Anat%C3%B3mico.png/revision/latest?cb=20190827222106&path-prefix=es",
                title = "Ver lugares",
                onClick = { navController.navigate("locationList") }
            )
        }

        item {
            HomeOptionCard(
                imageUrl = "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
                title = "Ver detalle de personaje",
                onClick = { navController.navigate("characterDetail/1") } // Cambia el ID del personaje según lo necesites
            )
        }
    }
}

@Composable
fun HomeOptionCard(imageUrl: String, title: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen en formato 4:3
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4 / 3f), // Relación de aspecto 4:3
                contentScale = ContentScale.Crop
            )

            // Título centrado debajo de la imagen
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
