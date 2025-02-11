package com.example.deber01

class ModeloDueño(
    var id: Int,
    var nombre: String,
    var telefono: String,
    var direccion: String,
    var mascotas: MutableList<Int>
) {
    override fun toString(): String {
        return "Dueño: $nombre, Dirección: $direccion, Teléfono: $telefono, Mascotas: $mascotas"
    }
}