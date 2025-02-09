package com.example.deber01

class BaseDatosDueño {
    companion object {
        val listaDueños = arrayListOf<ModeloDueño>()

        init {
            // Datos iniciales de prueba
            listaDueños.add(ModeloDueño(1, "Carlos Pérez", "0991234567", "Calle Falsa 123", mutableListOf()))
            listaDueños.add(ModeloDueño(2, "Ana Gómez", "0987654321", "Avenida Siempre Viva 742", mutableListOf()))
            listaDueños.add(ModeloDueño(3, "Juan Mera", "0987261538", "Avenida Simón Bolivar", mutableListOf()))
        }
    }
}