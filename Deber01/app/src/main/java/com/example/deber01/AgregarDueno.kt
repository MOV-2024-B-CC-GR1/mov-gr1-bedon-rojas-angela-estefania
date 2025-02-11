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

        val db = BaseDatosSQLite(this)

        btnGuardar.setOnClickListener {
            val nombre = edtNombre?.text.toString().trim()
            val telefono = edtTelefono?.text.toString().trim()
            val direccion = edtDireccion?.text.toString().trim()

            if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty()) {
                val nuevoDueño = ModeloDueño(0, nombre, telefono, direccion, mutableListOf())

                if (db.insertarDueño(nuevoDueño)) {
                    Toast.makeText(this, "Dueño agregado correctamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error al agregar dueño", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
