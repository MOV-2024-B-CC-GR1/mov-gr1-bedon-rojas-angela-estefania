package com.example.deber01

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var listViewDuenos: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private var listaDuenos = mutableListOf<ModeloDueño>()
    private lateinit var db: BaseDatosSQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = BaseDatosSQLite(this)

        listViewDuenos = findViewById(R.id.listaDuenos)
        val btnCrearDueno = findViewById<Button>(R.id.btnCrearDueno)
        val btnEliminarDueno = findViewById<Button>(R.id.btnEliminarDueno)
        val btnModificarDueno = findViewById<Button>(R.id.btnModificarDueno)
        val btnListaMascotas = findViewById<Button>(R.id.btnListaMascota)
        val verMapaButton = findViewById<Button>(R.id.btnVerMapa)

        // **Inicializar adapter antes de usarlo**
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, mutableListOf())
        listViewDuenos.adapter = adapter
        listViewDuenos.choiceMode = ListView.CHOICE_MODE_SINGLE

        // **Cargar y mostrar dueños en el ListView**
        cargarListaDuenos()

        verMapaButton.setOnClickListener {
            val posicion = listViewDuenos.checkedItemPosition
            if (posicion >= 0) {
                val duenoSeleccionado = listaDuenos[posicion]
                val intent = Intent(this, MapaActivity::class.java)
                intent.putExtra("duenoId", duenoSeleccionado.id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona un dueño para ver el mapa", Toast.LENGTH_SHORT).show()
            }
        }

        btnCrearDueno.setOnClickListener {
            val intent = Intent(this, AgregarDueno::class.java)
            startActivity(intent)
        }

        btnModificarDueno.setOnClickListener {
            val posicion = listViewDuenos.checkedItemPosition
            if (posicion >= 0) {
                val dueñoSeleccionado = listaDuenos[posicion]
                val intent = Intent(this, EditarDueño::class.java)
                intent.putExtra("duenoId", dueñoSeleccionado.id)
                startActivityForResult(intent, 2) // Usamos startActivityForResult para actualizar la lista
            } else {
                Toast.makeText(this, "Selecciona un dueño para editar", Toast.LENGTH_SHORT).show()
            }
        }

        btnEliminarDueno.setOnClickListener {
            val posicion = listViewDuenos.checkedItemPosition
            if (posicion >= 0) {
                val dueñoSeleccionado = listaDuenos[posicion]
                if (db.eliminarDueño(dueñoSeleccionado.id)) {
                    cargarListaDuenos()
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Dueño eliminado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Selecciona un dueño para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        // **Botón para ir a la lista de mascota**
        btnListaMascotas.setOnClickListener {
            val intent = Intent(this, Mascota::class.java)
            startActivity(intent)
        }

        listViewDuenos.setOnItemClickListener { _, view, position, _ ->
            listViewDuenos.setItemChecked(position, true)
            view.isSelected = true
        }
    }

    private fun cargarListaDuenos() {
        listaDuenos.clear()
        listaDuenos.addAll(db.obtenerDueños())

        // Obtener nombres de mascotas asociadas a cada dueño
        val listaTexto = listaDuenos.map { dueno ->
            val nombresMascotas = dueno.mascotas.joinToString(", ") { mascotaId ->
                val mascota = db.obtenerMascotaPorId(mascotaId) // Nuevo método
                mascota?.nombre ?: "Desconocido"
            }

            "ID: ${dueno.id} - ${dueno.nombre} - ${dueno.telefono} - Mascotas: $nombresMascotas"
        }

        // Actualizar el adaptador con la nueva lista
        adapter.clear()
        adapter.addAll(listaTexto)
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        cargarListaDuenos()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK) {
            cargarListaDuenos() // Recargar la lista después de editar
        }
    }

}
