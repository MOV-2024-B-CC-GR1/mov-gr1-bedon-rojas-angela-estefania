package com.example.deber01

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EditarDueño : AppCompatActivity() {
    private var duenoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_dueno)

        // Obtener vistas
        val inputNombre = findViewById<TextInputEditText>(R.id.inputEditNombreDueno)
        val inputTelefono = findViewById<TextInputEditText>(R.id.inputEditTelefonoDueno)
        val inputDireccion = findViewById<TextInputEditText>(R.id.inputEditDireccionDueno)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCambiosDueno)

        // Obtener datos del Intent
        duenoId = intent.getIntExtra("duenoId", -1)

        // Buscar el dueño en la base de datos
        val dueno = BaseDatosDueño.listaDueños.find { it.id == duenoId }

        // Mostrar datos en los campos si el dueño existe
        dueno?.let {
            inputNombre.setText(it.nombre)
            inputTelefono.setText(it.telefono)
            inputDireccion.setText(it.direccion)
        }

        // Guardar cambios
        btnGuardar.setOnClickListener {
            if (dueno != null) {
                dueno.nombre = inputNombre.text.toString()
                dueno.telefono = inputTelefono.text.toString()
                dueno.direccion = inputDireccion.text.toString()

                setResult(RESULT_OK) // Indicar que los cambios fueron guardados
                finish() // Volver a la actividad anterior
            }
        }
    }
}
