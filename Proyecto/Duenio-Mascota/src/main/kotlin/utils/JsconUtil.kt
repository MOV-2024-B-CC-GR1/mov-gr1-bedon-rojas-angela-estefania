package utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object JsonUtil {
    private val gson = Gson()

    // Guardar datos en un archivo JSON
    fun <T> guardarEnArchivo(datos: T, rutaArchivo: String) {
        val archivo = File(rutaArchivo)
        archivo.writeText(gson.toJson(datos))
    }

    // Leer datos desde un archivo JSON
    fun <T> cargarDesdeArchivo(rutaArchivo: String, typeToken: TypeToken<T>): T {
        val archivo = File(rutaArchivo)
        if (archivo.exists()) {
            return gson.fromJson(archivo.readText(), typeToken.type)
        }
        throw Exception("El archivo no existe: $rutaArchivo")
    }
}