@file:Suppress("UNUSED_PARAMETER")

package lesson7

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */

//Асимптотическая сложность О(N*M), где N-длина строки first, M-длина строки second.
//ПамятьО(k), где k- длина найденной подпоследовательности.
fun longestCommonSubSequence(first: String, second: String): String {
    val firstLength: Int = first.length
    val secondLength: Int = second.length

    val matrix = Array(firstLength + 1) { IntArray(secondLength + 1) }

    for (i in firstLength - 1 downTo 0) {
        for (j in secondLength - 1 downTo 0) {
            if (first[i] == second[j]) {
                matrix[i][j] = matrix[i + 1][j + 1] + 1
            } else {
                matrix[i][j] = matrix[i + 1][j].coerceAtLeast(matrix[i][j + 1])
            }
        }
    }

    var i = 0
    var j = 0
    var generalSubsequence = ""
    while (i < firstLength && j < secondLength) {
        if (first[i] == second[j]) {
            generalSubsequence += first[i]
            i++
            j++
        } else if (matrix[i + 1][j] >= matrix[i][j + 1]) {
            i++
        } else {
            j++
        }
    }
    return generalSubsequence
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */

//Асимптотическая сложность О(N^2), где N-длина входящего списка.
//ПамятьО(k), где k- длина найденной подпоследовательности.
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val matrix = Array(list.size) { IntArray(2) }
    var maxLength = 0

    for (i in list.indices) {
        matrix[i][0] = -1
        matrix[i][1] = 1
        for (j in i - 1 downTo 0) {
            if (list[i] > list[j]) {
                if (matrix[i][1] <= matrix[j][1] + 1) {
                    matrix[i][1] = matrix[j][1] + 1
                    matrix[i][0] = j
                }
            }
        }
        maxLength = maxLength.coerceAtLeast(matrix[i][1])
    }
    val maxSubsequence = mutableListOf<Int>()

    for (i in list.indices) {
        if (matrix[i][1] == maxLength) {
            var currentIndex = i
            while (currentIndex != -1) {
                maxSubsequence.add(list[currentIndex])
                currentIndex = matrix[currentIndex][0]
            }
            break
        }
    }
    maxSubsequence.reverse()
    return maxSubsequence
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */

// Задача не доделана, поэтому тесты не проходит.
fun shortestPathOnField(inputName: String): Int {
//    val currentPath = mutableMapOf(0 to 0)
//
//    File(inputName).forEachLine { line ->
//        val lineValue = line.split(" ")
//        var count = 0
//        for (element in lineValue) {
//            val pathLength = element.toInt()
//            if (count > 0) {
//
//            }
//            val valueFromCurrentPath = currentPath[count]!! + pathLength
//            currentPath[count] = valueFromCurrentPath
//            count++
//        }
//    }
    TODO()
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5