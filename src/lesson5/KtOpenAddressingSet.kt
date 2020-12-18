package lesson5

import java.lang.IllegalStateException

/**
 * Множество(таблица) с открытой адресацией на 2^bits элементов без возможности роста.
 */
class KtOpenAddressingSet<T : Any>(private val bits: Int) : AbstractMutableSet<T>() {
    init {
        require(bits in 2..31)
    }

    private val capacity = 1 shl bits

    private val storage = Array<Any?>(capacity) { null }

    override var size: Int = 0


    /**
     * Индекс в таблице, начиная с которого следует искать данный элемент
     */
    private fun T.startingIndex(): Int {
        return hashCode() and (0x7FFFFFFF shr (31 - bits))
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    override fun contains(element: T): Boolean = findIndex(element) != null

    //Асимптотическая сложность O(capacity). Худший случай: таблица полная, а искомый элемент находится в позиции startingIndex - 1.
    // память O(1)
    private fun findIndex(element: T): Int? {
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != element) {
            index = (index + 1) % capacity

            if (index == startingIndex) {
                return null
            }

            current = storage[index]
        }
        return index
    }


    /**
     * Добавление элемента в таблицу.
     *
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     *
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    override fun add(element: T): Boolean {
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                return false
            }
            index = (index + 1) % capacity
            check(index != startingIndex) { "Table is full" }
            current = storage[index]
        }
        storage[index] = element
        size++
        return true
    }

    /**
     * Удаление элемента из таблицы
     *
     * Если элемент есть в таблице, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     *
     * Спецификация: [java.util.Set.remove] (Ctrl+Click по remove)
     *
     * Средняя
     */

    //Асимптотическая сложность O(capacity), так как метод findIndex() в худшем случае перебирает все элементы.
    // само удаление элемента константно.
    // память O(1)
    override fun remove(element: T): Boolean {
        val elementIndex = findIndex(element)
        return if (elementIndex != null) {
            storage[elementIndex] = null
            size--
            true
        } else {
            false
        }
    }


    /**
     * Создание итератора для обхода таблицы
     *
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Средняя (сложная, если поддержан и remove тоже)
     */
    override fun iterator(): MutableIterator<T> = OpenAddressIterator()

    inner class OpenAddressIterator internal constructor() : MutableIterator<T> {

        @Suppress("UNCHECKED_CAST")
        private val storageWithOutNull: List<T> = storage.filterNotNull() as List<T>

        private var nextElementIndex = 0
        private var lastDeletedIndex = -1

        //Асимптотическая сложность O(1), память O(1)
        override fun hasNext(): Boolean {
            println(storageWithOutNull)
            return nextElementIndex < storageWithOutNull.size
        }

        //Асимптотическая сложность O(1), память O(1)
        override fun next(): T {
            if (!hasNext()) {
                throw IllegalStateException()
            }

            val nextElement = storageWithOutNull[nextElementIndex]
            nextElementIndex += 1
            return nextElement
        }

        //Асимптотическая сложность O(1)
        // память O(1)
        override fun remove() {
            val toDelete = nextElementIndex - 1
            if (nextElementIndex == 0 || lastDeletedIndex == toDelete) {
                throw IllegalStateException()
            }

            val currentElement = storageWithOutNull[toDelete]
            remove(currentElement)
            lastDeletedIndex = toDelete
        }
    }

    override fun toString(): String {
        return storage.contentToString()
    }
}