package com.example.deber01

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Mascota : AppCompatActivity() {
    private lateinit var listViewMascotas: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private var listaMascotas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota)

        // **Vinculación con los elementos del XML**
        listViewMascotas = findViewById(R.id.listaMascotas)
        val btnCrearMascota = findViewById<Button>(R.id.btnCrearMascota)
        val btnEliminarMascota = findViewById<Button>(R.id.btnEliminarMascota)
        val btnModificarMascota = findViewById<Button>(R.id.btnModificarMascota)
        val btnListaDuenos = findViewById<Button>(R.id.btnListaDuenos)

        // **Cargar y mostrar mascotas en el ListView**
        cargarListaMascotas()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, listaMascotas)
        listViewMascotas.adapter = adapter
        listViewMascotas.choiceMode = ListView.CHOICE_MODE_SINGLE

        // **Botón para agregar una nueva mascota**
        btnCrearMascota.setOnClickListener {
            val intent = Intent(this, AgregarMascota::class.java)
            startActivity(intent)
        }

        // **Botón para modificar una mascota seleccionada**
        btnModificarMascota.setOnClickListener {
            val posicion = listViewMascotas.checkedItemPosition
            if (posicion >= 0) {
                val mascotaSeleccionada = BaseDatosMascota.listaMascotas[posicion]
                val intent = Intent(this, EditarMascota::class.java)
                intent.putExtra("mascotaId", mascotaSeleccionada.id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona una mascota para editar", Toast.LENGTH_SHORT).show()
            }
        }

        // **Botón para eliminar una mascota seleccionada**
        btnEliminarMascota.setOnClickListener {
            val posicion = listViewMascotas.checkedItemPosition
            if (posicion >= 0) {
                val mascotaSeleccionada = BaseDatosMascota.listaMascotas[posicion]
                BaseDatosMascota.listaMascotas.remove(mascotaSeleccionada)
                cargarListaMascotas()
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Mascota eliminada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Selecciona una mascota para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        // **Botón para ir a la lista de dueños**
        btnListaDuenos.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // Asume que `MainActivity` es la lista de dueños
            startActivity(intent)
        }

        // **Al hacer clic en un item, lo marcamos como seleccionado**
        listViewMascotas.setOnItemClickListener { _, view, position, _ ->
            listViewMascotas.setItemChecked(position, true)
            view.isSelected = true
        }
    }

    // **Método para cargar la lista de mascotas desde la base de datos**
    private fun cargarListaMascotas() {
        listaMascotas.clear()

        listaMascotas.addAll(BaseDatosMascota.listaMascotas.map { mascota ->
            // Buscar el dueño correspondiente al ID almacenado en la mascota
            val dueno = BaseDatosDueño.listaDueños.find { it.id == mascota.idDueno }
            val nombreDueno = dueno?.nombre ?: "Desconocido"

            // Formato de la lista con el nombre del dueño
            "Mascota: ${mascota.nombre} - ${mascota.especie} - ${mascota.edad} años - Dueño: $nombreDueno"
        })
    }

    override fun onResume() {
        super.onResume()
        cargarListaMascotas()
        adapter.notifyDataSetChanged()
    }
}
