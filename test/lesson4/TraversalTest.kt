package lesson4

import org.junit.jupiter.api.Test

class TraversalTest {

    @Test
    fun traverse() {
        val trie = KtTrie()
        trie.add("artist")
        trie.add("file")
        trie.add("required")
        trie.add("are")
        trie.add("aristocrat")
        trie.add("find")
        trie.add("available")
        trie.add("fine")
        trie.add("foreign")

        trie.iterator()
    }
}