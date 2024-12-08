// Location.kt
package com.example.mendo.models

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

// LocationList.kt


data class LocationList(
    val results: List<Location> // Lista de ubicaciones
)
