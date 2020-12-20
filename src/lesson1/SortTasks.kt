@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import kotlin.Int.Companion.MAX_VALUE

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */

//Асимптотическая сложность O(N*logN), память O(N).
fun sortTimes(inputName: String, outputName: String) {
    val timeAM = mutableListOf<Time>()
    val timePM = mutableListOf<Time>()
    File(inputName).forEachLine { line ->
        require(Regex("""\d\d:\d\d:\d\d ((AM)|(PM))""").matches(line))
        val split = line.split(" ")
        if (split[1] == "AM") {
            timeAM.add(Time(split[0]))
        } else {
            timePM.add(Time(split[0]))
        }
    }

    File(outputName).bufferedWriter().use { writer ->
        timeAM.sorted().forEach { writer.write("$it AM\n") }
        timePM.sorted().forEach { writer.write("$it PM\n") }
    }
}

//класс для 12 часового формата времени(чч:мм:сс)
class Time(private val timeString: String) : Comparable<Time> {
    val time: List<String> = timeString.split(":")
    private val hours = validFormatHours(time[0].toInt()) % 12
    private val minutes = validFormat(time[1].toInt())
    private val seconds = validFormat(time[2].toInt())

    private fun validFormatHours(num: Int): Int {
        return if (num >= 13) {
            throw IllegalArgumentException()
        } else {
            num
        }
    }

    private fun validFormat(num: Int): Int {
        return if (num >= 60) {
            throw IllegalArgumentException()
        } else {
            num
        }
    }


    override operator fun compareTo(other: Time): Int {
        val comparedHours = hours.compareTo(other.hours)
        return if (comparedHours != 0) {
            comparedHours
        } else {
            val comparedMinutes = minutes.compareTo(other.minutes)
            if (comparedMinutes != 0)
                comparedMinutes
            else {
                seconds.compareTo(other.seconds)
            }
        }
    }

    override fun toString(): String = timeString
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */

//Асимптотическая сложность O(N*logN), память O(N)
fun sortAddresses(inputName: String, outputName: String) {
    val addressBook = sortedMapOf<Address, List<Person>>()
    File(inputName).forEachLine { line ->
        val parseLine = parseLine(line)
        val key = parseLine.address
        if (key in addressBook.keys) {
            val value = addressBook[key]?.toMutableList()
            value?.add(parseLine.person)
            if (value != null) {
                addressBook[key] = value
            }
        } else {
            addressBook[parseLine.address] = listOf(parseLine.person)
        }
    }
    File(outputName).bufferedWriter().use { writer ->
        for (address in addressBook.keys) {
            val occupants = addressBook[address]?.sorted()?.joinToString { "$it" }
            writer.write("$address - $occupants\n")
        }
    }
}


fun parseLine(line: String): OneAddress {
    val nameAndAddress = line.split(" - ")
    val name = nameAndAddress[0].split(" ")
    val person = Person(name[1], name[0])
    val addressLine = nameAndAddress[1].split(" ")
    val address = Address(addressLine[0], addressLine[1].toInt())
    return OneAddress(person, address)
}

class Person(private val firstName: String, private val lastName: String) : Comparable<Person> {
    override fun compareTo(other: Person): Int {
        val compareLastName = lastName.compareTo(other.lastName)
        val compareFirstName = firstName.compareTo(other.firstName)
        return if (compareLastName == 0) {
            compareFirstName
        } else {
            compareLastName
        }
    }

    override fun toString(): String {
        return "$lastName $firstName"
    }
}

class Address(private val street: String, private val house: Int) : Comparable<Address> {
    override fun compareTo(other: Address): Int {
        val compareStreet = street.compareTo(other.street)
        val compareHouse = house.compareTo(other.house)
        return if (compareStreet == 0) {
            compareHouse
        } else {
            compareStreet
        }
    }

    override fun toString(): String {
        return "$street $house"
    }
}

class OneAddress(val person: Person, val address: Address)


/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */

//Асимптотическая сложность O(N), память O(N)
fun sortTemperatures(inputName: String, outputName: String) {
    val temperatures = arrayOfNulls<Pair<Int, String>>(7731)
    File(inputName).forEachLine { line ->
        val index = (line.toDouble() * 10 + 2730).toInt()
        val reduplication = (temperatures[index]?.first ?: -1) + 1
        temperatures[index] = Pair(reduplication, line)
    }
    File(outputName).bufferedWriter().use { writer ->
        for (pair in temperatures) {
            if (pair != null) {
                for (reduplication in 0..pair.first) {
                    writer.write("${pair.second}\n")
                }

            }
        }
    }
}


/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */

//Асимптотическая сложность O(N), память O(N)
fun sortSequence(inputName: String, outputName: String) {
    val sequence = mutableListOf<Int>()
    File(inputName).forEachLine { line ->
        val number = line.toInt()
        sequence.add(number)
    }

    val duplicate = mutableMapOf<Int, Int>()
    var max = -1
    var minValue = MAX_VALUE
    for (el in sequence) {
        if (el !in duplicate.keys) {
            duplicate[el] = 1
        } else {
            val count = duplicate[el]
            if (count != null) {
                duplicate[el] = count + 1
            }
        }
    }
    for (pair in duplicate) {
        if (pair.value > max) {
            max = pair.value
        }
    }
    for (pair in duplicate) {
        if (pair.value == max && pair.key < minValue) {
            minValue = pair.key
        }
    }

    File(outputName).bufferedWriter().use { writer ->
        sequence.filter { it != minValue }.forEach { writer.write("$it\n") }
        for (i in 0 until max) {
            run { writer.write("$minValue\n") }
        }
    }
}


/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */


//Асимптотическая сложность O(N), память O(N)
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    var firstArrayIndex = 0
    var secondArrayIndex = first.size
    for (i in second.indices) {
        if (secondArrayIndex >= second.size ||
            (firstArrayIndex < first.size && first[firstArrayIndex] <= second[secondArrayIndex]!!)
        ) {
            second[i] = first[firstArrayIndex]
            firstArrayIndex += 1
        } else {
            second[i] = second[secondArrayIndex]
            secondArrayIndex += 1
        }
    }
}



