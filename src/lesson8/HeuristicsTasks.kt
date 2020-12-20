@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson8

import lesson6.Graph
import lesson6.Path
import lesson7.knapsack.Fill
import lesson7.knapsack.Item
import kotlin.math.ceil
import kotlin.random.Random

// Примечание: в этом уроке достаточно решить одну задачу

/**
 * Решить задачу о ранце (см. урок 6) любым эвристическим методом
 *
 * Очень сложная
 *
 * load - общая вместимость ранца, items - список предметов
 *
 * Используйте parameters для передачи дополнительных параметров алгоритма
 * (не забудьте изменить тесты так, чтобы они передавали эти параметры)
 */

//Попыталась реализовать алгоритм отжига. В целом он работает, но результаты выдает хуже чем жадный алгоритм.
//Не понимаю в чём ошиблась. Буду благодарна за подсказку.
//Асимптотическая сложность О(1), так как заданое кольчество итераций проходит
//Ресурсоемкость О(fillWithRandomItems())
fun fillKnapsackHeuristics(capacity: Int, items: List<Item>, vararg parameters: Any): Fill {
    val iterations = 100000
    var selectedItems = fillWithRandomItems(capacity, Fill(), items);

    for (i in 0..iterations) {
        val amount = amountOfItemsToReplace(i, iterations, selectedItems.items.size)
        val newSolution = fillWithRandomItems(capacity, selectedItems.removeItems(amount), items)
        if (newSolution.cost > selectedItems.cost || shouldPickWorseNewSolution(i, iterations)) {
            selectedItems = newSolution
        }
    }

    return selectedItems
}

//Асимптотическая сложность О(items)
//Ресурсоемкость О(capacity)
private fun fillWithRandomItems(capacity: Int, knapsack: Fill, items: List<Item>): Fill {
    var currentLoad = knapsack.weight()
    val insertableItems = items.toMutableList()
    insertableItems.shuffle()
    var selectedItems = Fill()
    for (item in insertableItems) {
        if (item.weight + currentLoad <= capacity) {
            selectedItems += Fill(item)
            currentLoad += item.weight
        }
    }

    return selectedItems
}

private fun amountOfItemsToReplace(currentIteration: Int, maxIterations: Int, totalItems: Int): Int =
    ceil(totalItems.toDouble() * reverseCompletionRatio(currentIteration, maxIterations)).toInt()

private fun shouldPickWorseNewSolution(currentIteration: Int, maxIterations: Int): Boolean {
    val startingProbabilityToTakeWorseSolution = 0.8
    return Random.nextDouble() * reverseCompletionRatio(
        currentIteration,
        maxIterations
    ) > (1 - startingProbabilityToTakeWorseSolution)
}

private fun reverseCompletionRatio(currentIteration: Int, maxIterations: Int): Double =
    (maxIterations - currentIteration).toDouble() / maxIterations.toDouble()

/**
 * Решить задачу коммивояжёра (см. урок 5) методом колонии муравьёв
 * или любым другим эвристическим методом, кроме генетического и имитации отжига
 * (этими двумя методами задача уже решена в под-пакетах annealing & genetic).
 *
 * Очень сложная
 *
 * Граф передаётся через получатель метода
 *
 * Используйте parameters для передачи дополнительных параметров алгоритма
 * (не забудьте изменить тесты так, чтобы они передавали эти параметры)
 */
fun Graph.findVoyagingPathHeuristics(vararg parameters: Any): Path {
    TODO()
}

