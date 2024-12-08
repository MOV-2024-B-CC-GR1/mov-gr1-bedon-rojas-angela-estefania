//package org.example.models
package models


data class Duenio(
    val id: Int,                   // Identificador único del dueño
    val nombre: String,            // Nombre del dueño
    val direccion: String,         // Dirección del dueño
    val telefono: String,          // Teléfono de contacto del dueño
    val mascotas: List<Int>        // Lista de IDs de mascotas asociadas al dueño
)