package com.example.mendo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.mendo.Screens.CharacterDetailScreen
import com.example.mendo.Screens.CharacterListScreen
import com.example.mendo.Screens.EpisodeListScreen
import com.example.mendo.Screens.HomeScreen
import com.example.mendo.Screens.LocationListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(


                title = {
                    Box(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .background(Color(0xFF1e2838)),// Ajusta la posición vertical del título

                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Rick and Morty",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White

                        )
                    }
                },
                navigationIcon = {
                    // Solo muestra el botón de regresar si no estás en la pantalla de inicio
                    if (navController.currentBackStackEntry?.destination?.route != "home") {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF1e2838)),
                modifier = Modifier.height(56.dp),// Ajustar la altura de la barra
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding) // Evita que el contenido quede tapado por la TopAppBar
        ) {
            composable("home") {
                HomeScreen(navController)
            }
            composable("characterList") {
                CharacterListScreen(navController = navController)
            }
            composable("episodeList") {
                EpisodeListScreen()
            }
            composable("locationList") {
                LocationListScreen()
            }
            composable("characterDetail/{id}") { backStackEntry ->
                val characterId = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
                CharacterDetailScreen(characterId)
            }
        }
    }
}
