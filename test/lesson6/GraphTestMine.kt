package lesson6

import lesson6.impl.GraphBuilder
import org.junit.jupiter.api.Test

class GraphTestMine {
    @Test
    fun graph() {
        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, a)
        }.build()
        println("Edges: ${graph.edges}")
        println("Vertex: ${graph.vertices}")
        println(graph.findEulerLoop())
    }

}