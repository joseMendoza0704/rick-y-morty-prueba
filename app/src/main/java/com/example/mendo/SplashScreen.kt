// SplashScreen.kt
package com.example.mendo

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mendo.network.ApiClient
import kotlinx.coroutines.launch
import com.example.mendo.models.Character


@Composable
fun SplashScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        try {
            // Simular carga de datos (cambiar por tu lógica)
            isLoading = false
            navController.navigate("home")
        } catch (e: Exception) {
            isLoading = false
            // Manejar error
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Image(
                painter = painterResource(id = R.drawable.rick), // Logo de la app
                contentDescription = "App Logo",
                modifier = Modifier.fillMaxSize(0.5f)
            )
        }
    }
}
