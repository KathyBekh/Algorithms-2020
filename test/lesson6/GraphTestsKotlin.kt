package lesson6

import lesson6.impl.GraphBuilder
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class GraphTestsKotlin : AbstractGraphTests() {

    @Test
    fun mustNotContainLoops() {
        val loopedGraph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            addConnection(a, d)
            addConnection(a, c)
            addConnection(e, b)
            addConnection(c, e)
            addConnection(b, d)
        }.build()

        val exception = assertThrows<IllegalArgumentException> { loopedGraph.largestIndependentVertexSet() }
        assertEquals(
            "Graph must not contain loops",
            exception.message
        )
    }

    @Test
    @Tag("6")
    fun testFindEulerLoop() {
        findEulerLoop { findEulerLoop() }
    }

    @Test
    @Tag("7")
    fun testMinimumSpanningTree() {
        minimumSpanningTree { minimumSpanningTree() }
    }

    @Test
    @Tag("8")
    fun testLargestIndependentVertexSet() {
        largestIndependentVertexSet { largestIndependentVertexSet() }
    }

    @Test
    @Tag("8")
    fun testLongestSimplePath() {
        longestSimplePath { longestSimplePath() }
    }

    @Test
    @Tag("6")
    fun testBaldaSearcher() {
        baldaSearcher { inputName, words -> baldaSearcher(inputName, words) }
    }
}