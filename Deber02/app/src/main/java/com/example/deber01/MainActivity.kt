package com.example.deber01

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var listViewDuenos: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private var listaDuenos = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // **Vinculación con los elementos del XML**
        listViewDuenos = findViewById(R.id.listaDuenos)
        val btnCrearDueno = findViewById<Button>(R.id.btnCrearDueno)
        val btnEliminarDueno = findViewById<Button>(R.id.btnEliminarDueno)
        val btnModificarDueno = findViewById<Button>(R.id.btnModificarDueno)
        val btnListaMascotas = findViewById<Button>(R.id.btnListaMascota)

        // **Cargar y mostrar dueños en el ListView**
        cargarListaDuenos()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, listaDuenos)
        listViewDuenos.adapter = adapter
        listViewDuenos.choiceMode = ListView.CHOICE_MODE_SINGLE

        // **Botón para agregar un nuevo dueño**
        btnCrearDueno.setOnClickListener {
            val intent = Intent(this, AgregarDueno::class.java)
            startActivity(intent)
        }

        // **Botón para modificar un dueño seleccionado**
        btnModificarDueno.setOnClickListener {
            val posicion = listViewDuenos.checkedItemPosition
            if (posicion >= 0) {
                val duenoSeleccionado = BaseDatosDueño.listaDueños[posicion]
                val intent = Intent(this, EditarDueño::class.java)
                intent.putExtra("duenoId", duenoSeleccionado.id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona un dueño para editar", Toast.LENGTH_SHORT).show()
            }
        }

        // **Botón para eliminar un dueño seleccionado**
        btnEliminarDueno.setOnClickListener {
            val posicion = listViewDuenos.checkedItemPosition
            if (posicion >= 0) {
                val duenoSeleccionado = BaseDatosDueño.listaDueños[posicion]
                BaseDatosDueño.listaDueños.remove(duenoSeleccionado)
                cargarListaDuenos()
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Dueño eliminado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Selecciona un dueño para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        // **Botón para ir a la lista de mascotas**
        btnListaMascotas.setOnClickListener {
            val intent = Intent(this, Mascota::class.java)
            startActivity(intent)
        }

        // **Al hacer clic en un item, lo marcamos como seleccionado**
        listViewDuenos.setOnItemClickListener { _, view, position, _ ->
            listViewDuenos.setItemChecked(position, true)
            view.isSelected = true
        }
    }

    // **Método para cargar la lista de dueños desde la base de datos**
    private fun cargarListaDuenos() {
        listaDuenos.clear()
        listaDuenos.addAll(BaseDatosDueño.listaDueños.map { "ID: ${it.id} - ${it.nombre} - ${it.telefono}" })
    }

    override fun onResume() {
        super.onResume()
        cargarListaDuenos()
        adapter.notifyDataSetChanged()
    }
}
