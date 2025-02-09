package com.example.deber01

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EditarMascota : AppCompatActivity() {
    private var mascotaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)

        // **Vinculación con los elementos del XML**
        val inputNombre = findViewById<TextInputEditText>(R.id.inputEditNombreMascota)
        val inputEspecie = findViewById<TextInputEditText>(R.id.inputEditEspecieMascota)
        val inputEdad = findViewById<TextInputEditText>(R.id.inputEditEdadMascota)
        val inputIdDueno = findViewById<TextInputEditText>(R.id.inputEditIdDuenoMascota)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCambiosMascota)

        // **Obtener datos del Intent**
        mascotaId = intent.getIntExtra("mascotaId", -1)

        // **Buscar la mascota en la base de datos**
        val mascota = BaseDatosMascota.listaMascotas.find { it.id == mascotaId }

        // **Si la mascota existe, rellenamos los campos**
        mascota?.let {
            inputNombre.setText(it.nombre)
            inputEspecie.setText(it.especie)
            inputEdad.setText(it.edad.toString())
            inputIdDueno.setText(it.idDueno.toString()) // No editable
        }

        // **Guardar cambios al presionar el botón**
        btnGuardar.setOnClickListener {
            if (mascota != null) {
                val nuevoNombre = inputNombre.text.toString().trim()
                val nuevaEspecie = inputEspecie.text.toString().trim()
                val nuevaEdadStr = inputEdad.text.toString().trim()

                if (nuevoNombre.isNotEmpty() && nuevaEspecie.isNotEmpty() && nuevaEdadStr.isNotEmpty()) {
                    val nuevaEdad = nuevaEdadStr.toIntOrNull()

                    if (nuevaEdad == null) {
                        Toast.makeText(this, "La edad debe ser un número válido", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    // **Actualizar datos en la lista**
                    mascota.nombre = nuevoNombre
                    mascota.especie = nuevaEspecie
                    mascota.edad = nuevaEdad

                    Toast.makeText(this, "Mascota editada correctamente", Toast.LENGTH_SHORT).show()

                    setResult(RESULT_OK) // **Indica que los cambios fueron guardados**
                    finish() // **Cerrar actividad y regresar**
                } else {
                    Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
