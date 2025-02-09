package com.example.deber01

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class AgregarMascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_mascota)

        // **Vinculación con los elementos del XML**
        val edtNombre = findViewById<TextInputLayout>(R.id.inputNombreMascota).editText
        val edtEspecie = findViewById<TextInputLayout>(R.id.inputEspecieMascota).editText
        val edtEdad = findViewById<TextInputLayout>(R.id.inputEdadMascota).editText
        val edtIdDueno = findViewById<TextInputLayout>(R.id.inputIdDuenoMascota).editText
        val btnGuardar = findViewById<Button>(R.id.btnGuardarMascota)

        btnGuardar.setOnClickListener {
            val nombre = edtNombre?.text.toString().trim()
            val especie = edtEspecie?.text.toString().trim()
            val edadStr = edtEdad?.text.toString().trim()
            val idDuenoStr = edtIdDueno?.text.toString().trim()

            if (nombre.isNotEmpty() && especie.isNotEmpty() && edadStr.isNotEmpty() && idDuenoStr.isNotEmpty()) {
                val edad = edadStr.toIntOrNull()
                val idDueno = idDuenoStr.toIntOrNull()

                if (edad == null || idDueno == null) {
                    Toast.makeText(this, "Edad e ID del dueño deben ser números", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // **Verificamos que el dueño exista**
                val dueno = BaseDatosDueño.listaDueños.find { it.id == idDueno }
                if (dueno == null) {
                    Toast.makeText(this, "El dueño con ID $idDueno no existe", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // **Generamos un ID para la nueva mascota**
                val nuevoId = if (BaseDatosMascota.listaMascotas.isNotEmpty()) {
                    BaseDatosMascota.listaMascotas.maxOf { it.id } + 1
                } else {
                    1
                }

                // **Creamos la nueva mascota**
                val nuevaMascota = ModeloMascota(nuevoId, nombre, especie, edad, idDueno)

                // **Guardamos la mascota en la base de datos**
                BaseDatosMascota.listaMascotas.add(nuevaMascota)

                // **Agregamos la mascota a la lista de IDs del dueño**
                dueno.mascotas.add(nuevoId)

                Toast.makeText(this, "Mascota agregada correctamente", Toast.LENGTH_SHORT).show()

                finish() // **Cierra la actividad y regresa a la anterior**
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
