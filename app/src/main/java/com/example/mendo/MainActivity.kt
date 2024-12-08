package com.example.mendo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyApp()
        }
    }
}

@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {  // Cambié el destino inicial a "home"
        composable("home") {
            HomeScreen(navController)
        }
        composable("characterList") {
            // Aquí se pasa el NavController correctamente
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


