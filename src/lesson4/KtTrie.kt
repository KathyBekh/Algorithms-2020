package lesson4

import java.lang.IllegalStateException

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
            this
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
        var nextElementIndex = 0
        var currentWord = ""

        init {
            traverse(root, "")
//            println(words)
        }

        private fun traverse(node: Node, word: String) {
            for (child in node.children) {
                if (child.key == 0.toChar()) {
                    words.add(word)
                } else {
                    traverse(child.value, word + child.key)
                }
            }
        }

        override fun hasNext(): Boolean {
            return nextElementIndex < words.size
        }

        override fun next(): String {

            if (!hasNext()) {
                throw IllegalStateException()
            }

            val word = words[nextElementIndex]
            nextElementIndex += 1
            return word
        }

        override fun remove() {

            if (currentWord == "") throw IllegalStateException()

            remove(currentWord)
            words.remove(currentWord)
            println(currentWord)
            currentWord = ""
        }
    }

}