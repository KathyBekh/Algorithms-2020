package lesson3

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

class IteratorTest {
    @Test
    fun shouldIteratorOnOneElement() {
        val bst = KtBinarySearchTree<String>()
        bst.add("1")
        var valueBst = ""

        for (i in bst) {
            valueBst = i
        }

        assert(bst.root!!.value == valueBst)
    }

    @Test
    fun shouldIteratorOnTwoElements() {
        val bst = KtBinarySearchTree<String>()
        bst.add("one")
        bst.add("two")
        val valueBst = mutableListOf<String>()

        for (i in bst) {
            valueBst.add(i)
        }

        assert(bst.root!!.value == valueBst[0])
        assert(bst.root!!.right!!.value == valueBst[1])
    }

    @Test
    fun shouldIteratorOnNElements() {
        /*
                 5
                /\
               3  7
              /\
             2  4
        */
        val bst = KtBinarySearchTree<Int>()
        bst.add(5)
        bst.add(3)
        bst.add(7)
        bst.add(2)
        bst.add(4)
        val expectedList = listOf(2, 3, 4, 5, 7)
        val receivedList = mutableListOf<Int>()

        for (i in bst) {
            receivedList.add(i)
        }

        assert(expectedList == receivedList)
    }
}