package repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import models.Mascota
import java.io.File
import java.io.IOException

object RMascota {
    private val gson = Gson()
    private val file = File("src/main/resources/mascotas.json")
    private val mascotas: MutableList<Mascota> = loadData()

    private fun loadData(): MutableList<Mascota> {
        return try {
            if (file.exists()) {
                val jsonString = file.readText()
                gson.fromJson(jsonString, object : TypeToken<MutableList<Mascota>>() {}.type)
            } else {
                mutableListOf()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    fun getAll(): List<Mascota> = mascotas.toList()

    fun getById(id: Int): Mascota? = mascotas.find { it.id == id }

    fun getByDuenioId(duenioId: Int): List<Mascota> = mascotas.filter { it.idDuenio == duenioId }

    fun create(mascota: Mascota) {
        mascotas.add(mascota)
        saveData()
    }

    fun update(updatedMascota: Mascota) {
        val index = mascotas.indexOfFirst { it.id == updatedMascota.id }
        if (index != -1) {
            mascotas[index] = updatedMascota
            saveData()
        }
    }

    fun delete(id: Int) {
        mascotas.removeAll { it.id == id }
        saveData()
    }

    private fun saveData() {
        val jsonString = gson.toJson(mascotas)
        file.writeText(jsonString)
    }
}