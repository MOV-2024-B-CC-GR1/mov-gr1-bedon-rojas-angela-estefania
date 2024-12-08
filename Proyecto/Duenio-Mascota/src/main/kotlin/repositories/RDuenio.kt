//package org.example.repositories
package repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import models.Duenio
import java.io.File
import java.io.IOException

object RDuenio {
    private val gson = Gson()
    private val file = File("src/main/resources/duenios.json")
    private val duenios: MutableList<Duenio> = loadData()

    // Carga los datos desde el archivo JSON
    private fun loadData(): MutableList<Duenio> {
        return try {
            if (file.exists()) {
                val jsonString = file.readText()
                gson.fromJson(jsonString, object : TypeToken<MutableList<Duenio>>() {}.type)
            } else {
                if (!file.parentFile.exists()) file.parentFile.mkdirs() // Crear carpeta si no existe
                file.createNewFile() // Crear archivo vacío si no existe
                mutableListOf()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    // Devuelve todos los dueños
    fun getAll(): List<Duenio> = duenios.toList()

    // Devuelve un dueño por ID
    fun getById(id: Int): Duenio? = duenios.find { it.id == id }

    // Agrega un nuevo dueño
    fun create(duenio: Duenio) {
        duenios.add(duenio)
        saveData()
    }

    // Actualiza un dueño existente
    fun update(updatedDuenio: Duenio) {
        val index = duenios.indexOfFirst { it.id == updatedDuenio.id }
        if (index != -1) {
            duenios[index] = updatedDuenio
            saveData()
        }
    }

    // Elimina un dueño por ID
    fun delete(id: Int) {
        duenios.removeAll { it.id == id }
        saveData()
    }

    // Guarda los datos en el archivo JSON
    private fun saveData() {
        try {
            if (!file.exists()) {
                if (!file.parentFile.exists()) file.parentFile.mkdirs() // Crear carpeta si no existe
                file.createNewFile() // Crear archivo vacío si no existe
            }
            val jsonString = gson.toJson(duenios)
            file.writeText(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}