package com.example.deber01

class ModeloMascota (
    var id: Int,
    var nombre: String,
    var especie: String,
    var edad: Int,
    var idDueno: Int  // Relación con el Dueño
) {
    override fun toString(): String {
        return "Mascota: $nombre, Especie: $especie, Edad: $edad, Dueño ID: $idDueno"
    }
}