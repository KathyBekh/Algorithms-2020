package lesson4

import org.junit.jupiter.api.Test

class TraversalTest {

    private fun fullTree(): KtTrie {
        val trie = KtTrie();
        trie.add("artist")
        trie.add("file")
        trie.add("required")
        trie.add("are")
        trie.add("aristocrat")
        trie.add("find")
        trie.add("available")
        trie.add("fine")
        trie.add("foreign")
        return trie
    }

    @Test
    fun traverse() {
        val trie = fullTree()
        trie.iterator()
    }

    @Test
    fun iteratorNext() {
        val controlList = mutableListOf<String>()
        val trie = fullTree()
        for (el in trie) {
            controlList.add(el)
        }
        println("tree is: $controlList")
    }

    @Test
    fun remove() {
        val trie = fullTree()
        val iter = trie.iterator()
        iter.next()
        iter.remove()
    }
}