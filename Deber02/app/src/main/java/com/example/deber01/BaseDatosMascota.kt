package com.example.deber01

class BaseDatosMascota {
    companion object {
        val listaMascotas = arrayListOf<ModeloMascota>()

        init {
            // Datos iniciales de prueba
            listaMascotas.add(ModeloMascota(1, "Firulais", "Perro", 3, 1))
            listaMascotas.add(ModeloMascota(2, "Michi", "Gato", 2, 1))
            listaMascotas.add(ModeloMascota(3, "Rex", "Perro", 5, 2))
        }
    }
}