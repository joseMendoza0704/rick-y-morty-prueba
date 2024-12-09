package com.example.mendo.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mendo.models.Character
import com.example.mendo.ViewModels.CharacterListViewModel
import com.example.mendo.ViewModels.Result


import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(navController: NavController) {
    val viewModel: CharacterListViewModel = viewModel()
    val charactersState by viewModel.characters.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }

    // Obtener la lista de personajes cuando la pantalla se muestre
    LaunchedEffect(true) {
        viewModel.getCharacters()
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1e2838)),
    ) {
        // TextField para buscar personajes
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color(0xFF625b71), RoundedCornerShape(8.dp)),
                label = { Text("Search Character",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                    ) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFF1e2838) // Color de fondo del TextField
                ),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White, // Color del texto
                    fontWeight = FontWeight.Bold // Texto en negrita
                )
            )


            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA1140A)),
                onClick = {
                    coroutineScope.launch {
                        viewModel.searchCharacterByName(searchQuery)
                    }
                }
            ) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar resultados de búsqueda o lista completa
        when (val result = charactersState) {
            is Result.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Result.Success<*> -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .background(Color(0xFF1e2838)),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre tarjetas
                ) {
                    items(result.data as List<Character>) { character ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            colors = CardDefaults.cardColors(containerColor =  Color(0xFF38761D)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                            onClick = {
                                // Navegar al detalle del personaje
                                navController.navigate("characterDetail/${character.id}")
                            }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally // Centramos elementos
                            ) {
                                // Imagen del personaje
                                Image(
                                    painter = rememberImagePainter(character.image),
                                    contentDescription = "Character Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(4 / 3f) // Relación de aspecto 4:3
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                // Texto centrado y en negrita
                                Text(
                                    text = character.name,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
            }
            is Result.Error -> {
                Text(
                    text = "Error: ${result.message}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {}
        }
    }
}
