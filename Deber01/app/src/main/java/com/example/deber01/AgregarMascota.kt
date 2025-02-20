package com.example.deber01

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class AgregarMascota : AppCompatActivity() {
    private lateinit var db: BaseDatosSQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_mascota)

        db = BaseDatosSQLite(this)

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

                val nuevaMascota = ModeloMascota(0, nombre, especie, edad, idDueno)

                if (db.insertarMascota(nuevaMascota)) {
                    Toast.makeText(this, "Mascota agregada correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al agregar mascota", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
