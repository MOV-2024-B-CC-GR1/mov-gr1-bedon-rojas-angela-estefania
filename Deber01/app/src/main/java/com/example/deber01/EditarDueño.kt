package com.example.deber01

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EditarDueño : AppCompatActivity() {
    private var duenoId: Int = -1
    private lateinit var db: BaseDatosSQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_dueno)

        // Inicializar la base de datos
        db = BaseDatosSQLite(this)

        // Obtener vistas
        val inputNombre = findViewById<TextInputEditText>(R.id.inputEditNombreDueno)
        val inputTelefono = findViewById<TextInputEditText>(R.id.inputEditTelefonoDueno)
        val inputDireccion = findViewById<TextInputEditText>(R.id.inputEditDireccionDueno)
        val inputLat = findViewById<TextInputEditText>(R.id.inputEditLatDueno)  // NUEVO
        val inputLng = findViewById<TextInputEditText>(R.id.inputEditLngDueno)  // NUEVO
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCambiosDueno)

        // Obtener ID del dueño desde el Intent
        duenoId = intent.getIntExtra("duenoId", -1)

        if (duenoId != -1) {
            // Obtener el dueño desde la base de datos
            val dueno = db.obtenerDueñoPorId(duenoId)

            // Mostrar los datos en los campos de texto
            dueno?.let {
                inputNombre.setText(it.nombre)
                inputTelefono.setText(it.telefono)
                inputDireccion.setText(it.direccion)
                inputLat.setText(it.lat.toString())  // NUEVO
                inputLng.setText(it.lng.toString())  // NUEVO
            }

            // Guardar cambios
            btnGuardar.setOnClickListener {
                val nuevoNombre = inputNombre.text.toString().trim()
                val nuevoTelefono = inputTelefono.text.toString().trim()
                val nuevaDireccion = inputDireccion.text.toString().trim()
                val nuevaLat = inputLat.text.toString().toDoubleOrNull() ?: 0.0
                val nuevaLng = inputLng.text.toString().toDoubleOrNull() ?: 0.0

                if (nuevoNombre.isNotEmpty() && nuevoTelefono.isNotEmpty() && nuevaDireccion.isNotEmpty()) {
                    val dueñoActualizado = ModeloDueño(
                        id = duenoId,
                        nombre = nuevoNombre,
                        telefono = nuevoTelefono,
                        direccion = nuevaDireccion,
                        mascotas = mutableListOf(),
                        lat = nuevaLat,  // NUEVO
                        lng = nuevaLng   // NUEVO
                    )

                    if (db.actualizarDueño(dueñoActualizado)) {
                        Toast.makeText(this, "Dueño actualizado correctamente", Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Error al cargar dueño", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
