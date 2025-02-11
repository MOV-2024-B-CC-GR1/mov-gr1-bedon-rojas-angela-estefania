package com.example.deber01

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class AgregarDueno : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_dueno)

        val edtNombre = findViewById<TextInputLayout>(R.id.inputNombreDueno).editText
        val edtTelefono = findViewById<TextInputLayout>(R.id.inputTelefonoDueno).editText
        val edtDireccion = findViewById<TextInputLayout>(R.id.inputDireccionDueno).editText
        val btnGuardar = findViewById<Button>(R.id.btnGuardarDueno)

        btnGuardar.setOnClickListener {
            val nombre = edtNombre?.text.toString().trim()
            val telefono = edtTelefono?.text.toString().trim()
            val direccion = edtDireccion?.text.toString().trim()

            if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty()) {
                // **Generamos el ID de manera dinámica**
                val nuevoId = if (BaseDatosDueño.listaDueños.isNotEmpty()) {
                    BaseDatosDueño.listaDueños.maxOf { it.id } + 1
                } else {
                    1
                }

                val nuevoDueño = ModeloDueño(nuevoId, nombre, telefono, direccion, mutableListOf())

                // **Agregamos a la lista en memoria**
                BaseDatosDueño.listaDueños.add(nuevoDueño)

                // **Mensaje de confirmación**
                Toast.makeText(this, "Dueño agregado correctamente", Toast.LENGTH_SHORT).show()

                // **Finaliza la actividad y vuelve a la pantalla anterior**
                finish()
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
