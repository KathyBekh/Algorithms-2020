@file:Suppress("UNUSED_PARAMETER")

package lesson2

import java.io.File
import kotlin.math.sqrt

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
//Асимптотическая сложность O(N^2), память O(N)
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    var pair = Pair(0, 0)
    var profit = -1
    val prices = mutableListOf<Int>()
    File(inputName).forEachLine { line ->
        prices.add(line.toInt())
    }
    val arrayPrices = prices.toIntArray()
    for (ind in 0..arrayPrices.size) {
        for (secInd in ind + 1 until arrayPrices.size) {
            val newProfit = prices[secInd] - prices[ind]
            if (newProfit > profit) {
                profit = newProfit
                pair = Pair(ind + 1, secInd + 1)
            }
        }
    }
    return pair
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 *
 * Общий комментарий: решение из Википедии для этой задачи принимается,
 * но приветствуется попытка решить её самостоятельно.
 */
//Думала что придумала свой алгоритм, но сравнив его с википедией, поняла что нет:(
//Асимптотическая сложность O(N), память O(1)
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    var survivor = 1
    for (men in 1..menNumber) {
        survivor = (survivor + choiceInterval - 1) % men + 1
    }
    return survivor
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
//Асимптотическая сложность O(N*M*K), память O(N*M*K),
// где N - длина первой строки, M - длина второй строки и K - длина самой длинной общей подстроки.
fun longestCommonSubstring(first: String, second: String): String {
    var subStr = ""
    var maxStr = ""
    val n = first.length
    val m = second.length
    val matrix = Array(n) { IntArray(m) { 0 } }
    for (index in 0 until n) {
        for (secIndex in 0 until m) {
            if (first[index] == second[secIndex]) {
                matrix[index][secIndex] = 1
            }
        }
    }
    for (index in 0 until n) {
        for (secIndex in 0 until m) {
            if (matrix[index][secIndex] == 1) {
                subStr = first[index].toString()
                var count = index + 1
                var secCount = secIndex + 1
                while (count < n && secCount < m && matrix[count][secCount] == 1) {
                    subStr += first[count]
                    count += 1
                    secCount += 1
                }
            }
            if (subStr.length > maxStr.length) {
                maxStr = subStr
            }
        }
    }
    return if (maxStr.length <= 1) {
        ""
    } else {
        maxStr
    }
}

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
//Асимптотическая сложность O(N*sqrt(N)), память O(1)
fun calcPrimesNumber(limit: Int): Int {
    var count = 0
    for (i in 0..limit) {
        if (isPrime(i)) {
            count += 1
        }
    }
    return count
}

fun isPrime(number: Int): Boolean {
    if (number <= 1) {
        return false
    }
    if (number <= 3) {
        return true
    }
    if (number % 2 == 0 || number % 3 == 0) {
        return false
    }
    for (i in 5..(sqrt(number.toDouble())).toInt()) {
        if (number % i == 0) {
            return false
        }
    }
    return true
}

