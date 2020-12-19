@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson8

import lesson6.Graph
import lesson6.Path
import lesson7.knapsack.Fill
import lesson7.knapsack.Item

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
fun fillKnapsackHeuristics(capacity: Int, items: List<Item>, vararg parameters: Any): Fill {
    val initialState = fillWithRandomItems(capacity, Fill(), items);
    var selectedItems = initialState

    println(selectedItems)
    return selectedItems
}

private fun fillWithRandomItems(capacity: Int, knapsack: Fill, items: List<Item>): Fill {
    var currentLoad = knapsack.weight()
    val insertableItems = items.toMutableList()
    var selectedItems = Fill()
    while (insertableItems.isNotEmpty()) {
        val nextItem = insertableItems.random()
        if (nextItem.weight + currentLoad <= capacity) {
            selectedItems += Fill(nextItem)
            currentLoad += nextItem.weight
        }
        insertableItems.remove(nextItem)
    }

    return selectedItems
}

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

