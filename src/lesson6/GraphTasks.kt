@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson6

import lesson6.impl.GraphBuilder
import java.util.Stack

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */

//Асимптотическая сложность О()
//Ресурсоемкость О()
fun Graph.findEulerLoop(): List<Graph.Edge> {
    if (vertices.isEmpty() || !eulerGraph()) {
        return emptyList()
    }

    val eulerLoop = mutableListOf<Graph.Edge>()
    val stackVertices = Stack<Graph.Vertex>()
    stackVertices.push(vertices.first())
    val edges = this.edges
    while (stackVertices.isNotEmpty()) {
        val currentVertex = stackVertices.peek()
        for (vertex in vertices) {
            val edge = getConnection(currentVertex, vertex) ?: continue
            if (edges.contains(edge)) {
                stackVertices.push(vertex)
                edges.remove(edge)
                break
            }
        }
        if (currentVertex == stackVertices.peek()) {
            stackVertices.pop()
            if (stackVertices.isNotEmpty()) {
                eulerLoop.add(getConnection(currentVertex, stackVertices.peek())!!)
            }
        }
    }
    return eulerLoop
}

//Асимптотическая сложность О(V), где V - количество вершин в графе
//Ресурсоемкость О(V+E)
fun Graph.eulerGraph(): Boolean {
    val connections = mutableMapOf<Graph.Vertex, MutableSet<Graph.Vertex>>()
    for (vertex in vertices) {
        connections[vertex] = getNeighbors(vertex).toMutableSet()
        if (connections[vertex]!!.size % 2 != 0) {
            return false
        }
    }
    return true
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */

//Асимптотическая сложность О(V), где V - количество вершин в графе
//Ресурсоемкость О(V+E)
fun Graph.minimumSpanningTree(): Graph {
    val spanningTree = GraphBuilder()
    if (vertices.isEmpty()) {
        return spanningTree.build()
    }

    val firstVertex = vertices.first()
    val vertexSet = mutableSetOf(firstVertex)
    val edgeSet = mutableSetOf<Graph.Edge>()

    fun recurse(vertex: Graph.Vertex) {
        for ((ver, edg) in getConnections(vertex)) {
            if (!vertexSet.contains(ver)) {
                vertexSet.add(ver)
                edgeSet.add(edg)
                recurse(ver)
            }
        }
    }

    recurse(firstVertex)

    spanningTree.addVertex(firstVertex.name)
    if (edgeSet.first().begin == firstVertex)
        for (edge in edgeSet) {
            spanningTree.addVertex(edge.end.name)
            spanningTree.addConnection(edge.begin, edge.end)
        }
    else for (edge in edgeSet) {
        spanningTree.addVertex(edge.begin.name)
        spanningTree.addConnection(edge.begin, edge.end)
    }

    return spanningTree.build()
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Если на входе граф с циклами, бросить IllegalArgumentException
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 */
fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {

    if (vertices.isEmpty()) return setOf()

    val all = mutableSetOf<Set<Graph.Vertex>>()
    for (vertex in vertices) {
        val set = mutableSetOf<Graph.Vertex>()
        val skip = mutableSetOf<Graph.Vertex>()
        vertices.stream().filter { next ->
            !this.getNeighbors(vertex).contains(next) && !skip.contains(next)
        }.forEach { next ->
            skip.addAll(this.getNeighbors(next))
            set.add(next)
        }
        all.add(set)
    }
    return all.maxByOrNull { it.size } ?: setOf()
}

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */
fun Graph.longestSimplePath(): Path {
    var path = Path()
    val stack = Stack<Path>()

    vertices.forEach { stack.push(Path(it)) }

    while (stack.isNotEmpty()) {
        val currentPath = stack.pop()
        if (path.length < currentPath.length) path = currentPath
        val neighbours = getNeighbors(currentPath.vertices[currentPath.length])
        for (ver in neighbours) {
            if (ver !in currentPath) stack.push(Path(currentPath, this, ver))
        }
    }
    return path
}

/**
 * Балда
 * Сложная
 *
 * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
 * поэтому задача присутствует в этом разделе
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    TODO()
}