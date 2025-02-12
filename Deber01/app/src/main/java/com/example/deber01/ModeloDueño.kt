package com.example.deber01

class ModeloDueño(
    var id: Int,
    var nombre: String,
    var telefono: String,
    var direccion: String,
    var mascotas: MutableList<Int>,
    var lat: Double = 0.0,  // Nuevo campo
    var lng: Double = 0.0  // Nuevo campo
) {
    override fun toString(): String {
        return "Dueño: $nombre, Dirección: $direccion, Teléfono: $telefono, Lat: $lat, Long: $lng"
    }
}
