import models.Duenio
import models.Mascota
import repositories.RDuenio
import repositories.RMascota

fun main() {
    while (true) {
        println("Seleccione una opción:")
        println("1. Ver dueños")
        println("2. Agregar dueño")
        println("3. Actualizar dueño")
        println("4. Eliminar dueño")
        println("5. Ver mascotas por dueño")
        println("6. Agregar mascota")
        println("7. Actualizar mascota")
        println("8. Eliminar mascota")
        println("9. Salir")

        when (readlnOrNull()?.toIntOrNull() ?: 0) {
            1 -> verDuenios()
            2 -> agregarDuenio()
            3 -> actualizarDuenio()
            4 -> eliminarDuenio()
            5 -> verMascotasPorDuenio()
            6 -> agregarMascota()
            7 -> actualizarMascota()
            8 -> eliminarMascota()
            9 -> return
            else -> println("Opción no válida.")
        }
    }
}

fun verDuenios() {
    val duenios = RDuenio.getAll()
    if (duenios.isEmpty()) {
        println("No hay dueños registrados.")
    } else {
        println("Dueños:")
        duenios.forEach { println(it) }
    }
}

fun agregarDuenio() {
    println("Ingrese el nombre del dueño:")
    val nombre = readlnOrNull().toString()

    println("Ingrese la dirección:")
    val direccion = readlnOrNull().toString()

    println("Ingrese el teléfono:")
    val telefono = readlnOrNull().toString()

    val nuevoDuenio = Duenio(
        id = RDuenio.getAll().size + 1,
        nombre = nombre,
        direccion = direccion,
        telefono = telefono,
        mascotas = listOf()
    )

    RDuenio.create(nuevoDuenio)
    println("Dueño agregado correctamente: $nuevoDuenio")
}

fun actualizarDuenio() {
    println("Ingrese el ID del dueño a actualizar:")
    val idDuenio = readlnOrNull()?.toIntOrNull()

    if (idDuenio != null) {
        val duenioExistente = RDuenio.getById(idDuenio)

        if (duenioExistente != null) {
            println("Ingrese el nuevo nombre del dueño (dejar en blanco para mantener el actual):")
            val nombre = readlnOrNull()?.takeIf { it.isNotEmpty() } ?: duenioExistente.nombre

            println("Ingrese la nueva dirección (dejar en blanco para mantener la actual):")
            val direccion = readlnOrNull()?.takeIf { it.isNotEmpty() } ?: duenioExistente.direccion

            println("Ingrese el nuevo teléfono (dejar en blanco para mantener el actual):")
            val telefono = readlnOrNull()?.takeIf { it.isNotEmpty() } ?: duenioExistente.telefono

            val duenioActualizado = Duenio(
                id = idDuenio,
                nombre = nombre,
                direccion = direccion,
                telefono = telefono,
                mascotas = duenioExistente.mascotas
            )

            RDuenio.update(duenioActualizado)
            println("Dueño actualizado correctamente: $duenioActualizado")
        } else {
            println("No se encontró ningún dueño con el ID proporcionado.")
        }
    } else {
        println("ID del dueño no válido.")
    }
}

fun eliminarDuenio() {
    println("Ingrese el ID del dueño a eliminar:")
    val idDuenio = readlnOrNull()?.toIntOrNull()

    if (idDuenio != null) {
        val duenioExistente = RDuenio.getById(idDuenio)

        if (duenioExistente != null) {
            RDuenio.delete(idDuenio)
            println("Dueño eliminado correctamente.")
        } else {
            println("No se encontró ningún dueño con el ID proporcionado.")
        }
    } else {
        println("ID del dueño no válido.")
    }
}

fun verMascotasPorDuenio() {
    println("Ingrese el ID del dueño:")
    val idDuenio = readlnOrNull()?.toIntOrNull()

    if (idDuenio != null) {
        val mascotas = RMascota.getByDuenioId(idDuenio)

        if (mascotas.isEmpty()) {
            println("No hay mascotas registradas para este dueño.")
        } else {
            println("Mascotas del dueño con ID $idDuenio:")
            mascotas.forEach { println(it) }
        }
    } else {
        println("ID del dueño no válido.")
    }
}

fun agregarMascota() {
    println("Ingrese el nombre de la mascota:")
    val nombre = readlnOrNull().toString()

    println("Ingrese el tipo de mascota (por ejemplo, perro, gato):")
    val tipo = readlnOrNull().toString()

    println("Ingrese la edad de la mascota:")
    val edad = readlnOrNull()?.toIntOrNull() ?: 0

    println("Ingrese el ID del dueño:")
    val idDuenio = readlnOrNull()?.toIntOrNull()

    if (idDuenio != null) {
        val duenio = RDuenio.getById(idDuenio)

        if (duenio != null) {
            val nuevaMascota = Mascota(
                id = RMascota.getAll().size + 1,
                nombre = nombre,
                tipo = tipo,
                edad = edad,
                idDuenio = idDuenio
            )

            // Agregar la mascota al repositorio de mascotas
            RMascota.create(nuevaMascota)

            // Actualizar la lista de mascotas del dueño
            val duenioActualizado = duenio.copy(
                mascotas = duenio.mascotas + nuevaMascota.id
            )
            RDuenio.update(duenioActualizado)

            println("Mascota agregada correctamente: $nuevaMascota")
        } else {
            println("No se encontró ningún dueño con el ID proporcionado.")
        }
    } else {
        println("ID del dueño no válido.")
    }
}


fun actualizarMascota() {
    println("Ingrese el ID de la mascota a actualizar:")
    val idMascota = readlnOrNull()?.toIntOrNull()

    if (idMascota != null) {
        val mascotaExistente = RMascota.getById(idMascota)

        if (mascotaExistente != null) {
            println("Ingrese el nuevo nombre de la mascota (dejar en blanco para mantener el actual):")
            val nombre = readlnOrNull()?.takeIf { it.isNotEmpty() } ?: mascotaExistente.nombre

            println("Ingrese el nuevo tipo de mascota (dejar en blanco para mantener el actual):")
            val tipo = readlnOrNull()?.takeIf { it.isNotEmpty() } ?: mascotaExistente.tipo

            println("Ingrese la nueva edad de la mascota (dejar en blanco para mantener la actual):")
            val edad = readlnOrNull()?.toIntOrNull() ?: mascotaExistente.edad

            println("Ingrese el nuevo ID del dueño (dejar en blanco para mantener el actual):")
            val idDuenio = readlnOrNull()?.toIntOrNull() ?: mascotaExistente.idDuenio

            val mascotaActualizada = Mascota(
                id = idMascota,
                nombre = nombre,
                tipo = tipo,
                edad = edad,
                idDuenio = idDuenio
            )

            RMascota.update(mascotaActualizada)
            println("Mascota actualizada correctamente: $mascotaActualizada")
        } else {
            println("No se encontró ninguna mascota con el ID proporcionado.")
        }
    } else {
        println("ID de mascota no válido.")
    }
}

fun eliminarMascota() {
    println("Ingrese el ID de la mascota a eliminar:")
    val idMascota = readlnOrNull()?.toIntOrNull()

    if (idMascota != null) {
        val mascotaExistente = RMascota.getById(idMascota)

        if (mascotaExistente != null) {
            RMascota.delete(idMascota)
            println("Mascota eliminada correctamente.")
        } else {
            println("No se encontró ninguna mascota con el ID proporcionado.")
        }
    } else {
        println("ID de mascota no válido.")
    }
}