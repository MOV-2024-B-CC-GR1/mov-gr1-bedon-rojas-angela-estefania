package com.example.deber01

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Mascota : AppCompatActivity() {
    private lateinit var listViewMascotas: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var db: BaseDatosSQLite
    private var listaMascotas = mutableListOf<ModeloMascota>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota)

        db = BaseDatosSQLite(this)

        listViewMascotas = findViewById(R.id.listaMascotas)
        val btnCrearMascota = findViewById<Button>(R.id.btnCrearMascota)
        val btnEliminarMascota = findViewById<Button>(R.id.btnEliminarMascota)
        val btnModificarMascota = findViewById<Button>(R.id.btnModificarMascota)
        val btnListaDuenos = findViewById<Button>(R.id.btnListaDuenos)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, mutableListOf())
        listViewMascotas.adapter = adapter
        listViewMascotas.choiceMode = ListView.CHOICE_MODE_SINGLE

        cargarListaMascotas()

        btnCrearMascota.setOnClickListener {
            val intent = Intent(this, AgregarMascota::class.java)
            startActivity(intent)
        }

        btnEliminarMascota.setOnClickListener {
            val posicion = listViewMascotas.checkedItemPosition
            if (posicion >= 0) {
                val mascotaSeleccionada = listaMascotas[posicion]
                if (db.eliminarMascota(mascotaSeleccionada.id)) {
                    cargarListaMascotas()
                    Toast.makeText(this, "Mascota eliminada", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Selecciona una mascota para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        // **Botón para editar una mascota**
        btnModificarMascota.setOnClickListener {
            val posicion = listViewMascotas.checkedItemPosition
            if (posicion >= 0) {
                val mascotaSeleccionada = listaMascotas[posicion]
                val intent = Intent(this, EditarMascota::class.java)
                intent.putExtra("mascotaId", mascotaSeleccionada.id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona una mascota para editar", Toast.LENGTH_SHORT).show()
            }
        }

        // **Botón para ir a la lista de dueños**
        btnListaDuenos.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        listViewMascotas.setOnItemClickListener { _, view, position, _ ->
            listViewMascotas.setItemChecked(position, true)
            view.isSelected = true
        }
    }

    private fun cargarListaMascotas() {
        listaMascotas.clear()
        listaMascotas.addAll(db.obtenerMascotas())

        val listaTexto = listaMascotas.map { mascota ->
            val dueno = db.obtenerDueñoPorId(mascota.idDueno) // Obtener el dueño
            val nombreDueno = dueno?.nombre ?: "Desconocido" // Si no encuentra el dueño, pone "Desconocido"

            "Mascota: ${mascota.nombre} - ${mascota.especie} - ${mascota.edad} años - Dueño: $nombreDueno"
        }

        adapter.clear()
        adapter.addAll(listaTexto)
        adapter.notifyDataSetChanged()
    }


    override fun onResume() {
        super.onResume()
        cargarListaMascotas()
    }
}
