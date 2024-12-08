package models

data class Mascota(
    val id: Int,                   // Identificador único de la mascota
    val nombre: String,            // Nombre de la mascota
    val tipo: String,              // Tipo de animal (perro, gato, etc.)
    val edad: Int,                 // Edad de la mascota
    val idDuenio: Int              // ID del dueño de la mascota
)