package com.example.deber01

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class AgregarDueno : AppCompatActivity() {
    private lateinit var db: BaseDatosSQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_dueno)

        // Instanciamos la DB
        db = BaseDatosSQLite(this)

        // Referencias a campos de texto
        val edtNombre = findViewById<TextInputLayout>(R.id.inputNombreDueno).editText
        val edtTelefono = findViewById<TextInputLayout>(R.id.inputTelefonoDueno).editText
        val edtDireccion = findViewById<TextInputLayout>(R.id.inputDireccionDueno).editText
        val edtLat = findViewById<TextInputLayout>(R.id.inputLatDueno).editText      // NUEVO
        val edtLng = findViewById<TextInputLayout>(R.id.inputLngDueno).editText      // NUEVO

        val btnGuardar = findViewById<Button>(R.id.btnGuardarDueno)

        btnGuardar.setOnClickListener {
            val nombre = edtNombre?.text.toString().trim()
            val telefono = edtTelefono?.text.toString().trim()
            val direccion = edtDireccion?.text.toString().trim()

            // Convertir a Double la lat/lng
            val lat = edtLat?.text.toString().toDoubleOrNull() ?: 0.0
            val lng = edtLng?.text.toString().toDoubleOrNull() ?: 0.0

            // Validar campos
            if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty()) {
                val nuevoDueño = ModeloDueño(
                    id = 0,            // Se autogenera en la BD
                    nombre = nombre,
                    telefono = telefono,
                    direccion = direccion,
                    mascotas = mutableListOf(),
                    lat = lat,         // NUEVO
                    lng = lng          // NUEVO
                )

                if (db.insertarDueño(nuevoDueño)) {
                    Toast.makeText(this, "Dueño agregado correctamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error al agregar dueño", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Todos los campos (menos lat/lng) son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
