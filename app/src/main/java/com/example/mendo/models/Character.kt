package com.example.mendo.models



data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)

data class CharacterList(
    val results: List<Character> // Esta es la lista de personajes que retorna la API
)