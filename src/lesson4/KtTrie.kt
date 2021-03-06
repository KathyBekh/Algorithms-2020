package lesson4

/**
 * Префиксное дерево для строк
 */
class KtTrie : AbstractMutableSet<String>(), MutableSet<String> {

    private class Node {
        val children: MutableMap<Char, Node> = linkedMapOf()
    }

    private var root = Node()

    override var size: Int = 0
        private set

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()

    //Асимптотическая сложность O(n), где n - длина удаляемого слова.
    private fun findNode(element: String): Node? {
        var current = root
        for (char in element) {
            current = current.children[char] ?: return null
        }
        return current
    }

    override fun contains(element: String): Boolean =
        findNode(element.withZero()) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        val current = findNode(element) ?: return false
        if (current.children.remove(0.toChar()) != null) {
            size--
            return true
        }
        return false
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Сложная
     */

    override fun iterator(): MutableIterator<String> = TrieIterator()

    inner class TrieIterator internal constructor() : MutableIterator<String> {

        private val words = mutableListOf<String>()
        private var nextElementIndex = 0
        private var lastDeletedIndex = -1

        init {
            traverse(root, "")
        }

        //Асимптотическая сложность O(Nodes), где Nodes - количество узлов в дереве
        // память O(n), где n-количество слов в дереве
        private fun traverse(node: Node, word: String) {
            for (child in node.children) {
                if (child.key == 0.toChar()) {
                    words.add(word)
                } else {
                    traverse(child.value, word + child.key)
                }
            }
        }

        //Асимптотическая сложность O(1), память O(1)
        override fun hasNext(): Boolean {
            return nextElementIndex < words.size
        }

        //Асимптотическая сложность O(1), память O(1)
        override fun next(): String {

            if (!hasNext()) {
                throw IllegalStateException()
            }

            val word = words[nextElementIndex]
            nextElementIndex += 1
            return word
        }

        //Асимптотическая сложность O(n), где n - длина удаляемого слова.
        // память O(1)
        override fun remove() {
            val toDelete = nextElementIndex - 1
            if (nextElementIndex == 0 || lastDeletedIndex == toDelete) {
                throw IllegalStateException()
            }

            val currentWord = words[toDelete]
            remove(currentWord)
            lastDeletedIndex = toDelete
        }
    }
}