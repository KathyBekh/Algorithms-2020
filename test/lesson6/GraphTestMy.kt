package lesson6

import lesson6.impl.GraphBuilder
import org.junit.jupiter.api.Test

class GraphTestMy {
    @Test
    fun graph() {
        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, a)
            addConnection(a, c)
        }.build()
        println("Edgos: ${graph.edges}")
        println("Vertex: ${graph.vertices}")
    }
}