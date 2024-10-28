import java.util.LinkedList
import java.util.Stack

data class Orden(
    val nombreCliente: String,
    val pupusas: List<Pupusa>
)

data class Pupusa(
    val tipo: String,
    val cantidad: Int
)

fun main() {
    val sistema = PupuseriaSystem()
    var opcion: Int?

    do {
        sistema.mostrarMenu()
        opcion = readLine()?.toIntOrNull()
        
        if (opcion == null || opcion !in 1..5) {
            println("Opción no válida. Por favor, ingrese un número entre 1 y 5.")
            continue
        }

        when (opcion) {
            1 -> sistema.crearYRegistrarOrden()
            2 -> sistema.verOrdenesPendientes()
            3 -> sistema.despacharOrden()
            4 -> sistema.verOrdenesDespachadas()
            5 -> println("Saliendo del programa...")
        }
    } while (opcion != 5)
}

class PupuseriaSystem {
    private val ordenesPendientes: LinkedList<Orden> = LinkedList()
    private val ordenesDespachadas: Stack<Orden> = Stack()

    // Método para mostrar el menú
    fun mostrarMenu() {
        println("\nBienvenido a la Pupusería \"El Comalito\"")
        println("Seleccione una opción:")
        println("1. Registrar una nueva orden")
        println("2. Ver órdenes pendientes")
        println("3. Despachar una orden")
        println("4. Ver órdenes despachadas")
        println("5. Salir")
    }

    // Método para crear y registrar una orden
    fun crearYRegistrarOrden() {
        println("Ingrese el nombre del cliente:")
        val nombreCliente = readLine()?.takeIf { it.isNotBlank() } ?: run {
            println("El nombre del cliente no puede estar vacío.")
            return
        }

        val pupusas = mutableListOf<Pupusa>()
        println("¿Cuántos tipos de pupusas desea ordenar?")
        val tipos = readLine()?.toIntOrNull()
        
        if (tipos == null || tipos <= 0) {
            println("Debe ingresar un número válido de tipos de pupusas.")
            return
        }

        for (i in 1..tipos) {
            println("Ingrese el tipo de pupusa #$i:")
            val tipo = readLine()?.takeIf { it.isNotBlank() } ?: run {
                println("El tipo de pupusa no puede estar vacío.")
                return
            }

            println("Ingrese la cantidad de pupusas de $tipo:")
            val cantidad = readLine()?.toIntOrNull() ?: -1
            if (cantidad <= 0) {
                println("La cantidad de pupusas debe ser un número mayor a cero.")
                return
            }

            pupusas.add(Pupusa(tipo, cantidad))
        }

        // Registrar la orden después de crearla correctamente
        registrarOrden(nombreCliente, pupusas)
    }

    // Método para registrar una nueva orden
    private fun registrarOrden(nombreCliente: String, pupusas: List<Pupusa>) {
        val orden = Orden(nombreCliente, pupusas)
        ordenesPendientes.add(orden)  // Añadir a la cola
        println("Orden registrada para $nombreCliente")
    }

    // Método para ver órdenes pendientes
    fun verOrdenesPendientes() {
        if (ordenesPendientes.isEmpty()) {
            println("No hay órdenes pendientes.")
        } else {
            println("Órdenes pendientes:")
            ordenesPendientes.forEach { orden ->
                println("${orden.nombreCliente}: ${orden.pupusas.joinToString { "${it.cantidad} pupusas de ${it.tipo}" }}")
            }
        }
    }

    // Método para despachar una orden
    fun despacharOrden() {
        if (ordenesPendientes.isNotEmpty()) {
            val orden = ordenesPendientes.poll()
            ordenesDespachadas.push(orden)

            // Formato de salida específico solicitado
            val detalleOrden = orden.pupusas.joinToString(", ") { "${it.cantidad} pupusas de ${it.tipo}" }
            println("Despachando la orden de: ${orden.nombreCliente}: $detalleOrden")
        } else {
            println("No hay órdenes pendientes para despachar.")
        }
    }

    // Método para ver órdenes despachadas
    fun verOrdenesDespachadas() {
        if (ordenesDespachadas.isEmpty()) {
            println("No hay órdenes despachadas.")
        } else {
            println("Órdenes despachadas:")
            ordenesDespachadas.forEach { orden ->
                println("${orden.nombreCliente}: ${orden.pupusas.joinToString { "${it.cantidad} pupusas de ${it.tipo}" }}")
            }
        }
    }
}
