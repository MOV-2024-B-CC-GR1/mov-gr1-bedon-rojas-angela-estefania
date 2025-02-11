package com.example.deber01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatosSQLite(context: Context) : SQLiteOpenHelper(context, "DueñosDB", null, 2) {
    override fun onCreate(db: SQLiteDatabase) {
        val crearTablaDueños = """
            CREATE TABLE IF NOT EXISTS Dueños (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                telefono TEXT NOT NULL,
                direccion TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(crearTablaDueños)

        val crearTablaMascotas = """
            CREATE TABLE IF NOT EXISTS Mascotas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                especie TEXT NOT NULL,
                edad INTEGER NOT NULL,
                idDueno INTEGER NOT NULL,
                FOREIGN KEY (idDueno) REFERENCES Dueños(id) ON DELETE CASCADE
            )
        """.trimIndent()
        db.execSQL(crearTablaMascotas)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Mascotas")
        db.execSQL("DROP TABLE IF EXISTS Dueños")
        onCreate(db)
    }

    // **Insertar dueño**
    fun insertarDueño(dueño: ModeloDueño): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues()
        valores.put("nombre", dueño.nombre)
        valores.put("telefono", dueño.telefono)
        valores.put("direccion", dueño.direccion)
        val resultado = db.insert("Dueños", null, valores)
        db.close()
        return resultado != -1L
    }

    // **Obtener todos los dueños con sus mascotas**
    fun obtenerDueños(): MutableList<ModeloDueño> {
        val listaDueños = mutableListOf<ModeloDueño>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Dueños", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val telefono = cursor.getString(2)
                val direccion = cursor.getString(3)

                // Obtener mascotas asociadas a este dueño
                val listaMascotas = mutableListOf<Int>()
                val cursorMascotas = db.rawQuery("SELECT id FROM Mascotas WHERE idDueno = ?", arrayOf(id.toString()))

                if (cursorMascotas.moveToFirst()) {
                    do {
                        listaMascotas.add(cursorMascotas.getInt(0))
                    } while (cursorMascotas.moveToNext())
                }
                cursorMascotas.close()

                // Agregar dueño a la lista con sus mascotas
                listaDueños.add(ModeloDueño(id, nombre, telefono, direccion, listaMascotas))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaDueños
    }

    // Obtener un dueño por su ID
    fun obtenerDueñoPorId(id: Int): ModeloDueño? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Dueños WHERE id = ?", arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val dueno = ModeloDueño(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                mutableListOf() // No es necesario cargar mascotas aquí
            )
            cursor.close()
            db.close()
            dueno
        } else {
            cursor.close()
            db.close()
            null
        }
    }


    // **Eliminar dueño por ID**
    fun eliminarDueño(id: Int): Boolean {
        val db = this.writableDatabase
        val resultado = db.delete("Dueños", "id=?", arrayOf(id.toString()))
        db.close()
        return resultado > 0
    }

    // **Actualizar dueño**
    fun actualizarDueño(dueño: ModeloDueño): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues()
        valores.put("nombre", dueño.nombre)
        valores.put("telefono", dueño.telefono)
        valores.put("direccion", dueño.direccion)
        val resultado = db.update("Dueños", valores, "id=?", arrayOf(dueño.id.toString()))
        db.close()
        return resultado > 0
    }

    // **Insertar mascota**
    fun insertarMascota(mascota: ModeloMascota): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues()
        valores.put("nombre", mascota.nombre)
        valores.put("especie", mascota.especie)
        valores.put("edad", mascota.edad)
        valores.put("idDueno", mascota.idDueno)

        val resultado = db.insert("Mascotas", null, valores)
        db.close()
        return resultado != -1L
    }

    // **Obtener todas las mascotas**
    fun obtenerMascotas(): MutableList<ModeloMascota> {
        val listaMascotas = mutableListOf<ModeloMascota>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Mascotas", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val especie = cursor.getString(2)
                val edad = cursor.getInt(3)
                val idDueno = cursor.getInt(4)

                listaMascotas.add(ModeloMascota(id, nombre, especie, edad, idDueno))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaMascotas
    }

    fun obtenerMascotaPorId(id: Int): ModeloMascota? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Mascotas WHERE id = ?", arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val mascota = ModeloMascota(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getInt(4) // idDueno
            )
            cursor.close()
            db.close()
            mascota
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    // **Eliminar mascota por ID**
    fun eliminarMascota(id: Int): Boolean {
        val db = this.writableDatabase
        val resultado = db.delete("Mascotas", "id=?", arrayOf(id.toString()))
        db.close()
        return resultado > 0
    }

    // **Actualizar datos de una mascota**
    fun actualizarMascota(mascota: ModeloMascota): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues()
        valores.put("nombre", mascota.nombre)
        valores.put("especie", mascota.especie)
        valores.put("edad", mascota.edad)

        val resultado = db.update("Mascotas", valores, "id=?", arrayOf(mascota.id.toString()))
        db.close()
        return resultado > 0
    }
}
