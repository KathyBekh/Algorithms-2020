package lesson3

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull

class RemoveTests {

    @Test
    fun shouldRemoveRoot() {
        val bst = KtBinarySearchTree<Int>()
        bst.add(1)

        bst.remove(1)

        assertFalse(bst.contains(1))
        assert(bst.size == 0)
    }

    @Test
    fun shouldRemoveNodeWithNoChildren() {
        val bst = KtBinarySearchTree<Int>()
        bst.add(1)
        bst.add(2)

        bst.remove(2)

        assertFalse(bst.contains(2))
        assert(bst.size == 1)
    }

    @Test
    fun shouldMoveLeftChildUp() {
        val bst = KtBinarySearchTree<Int>()
        bst.add(1)
        bst.add(3)
        bst.add(2)

        bst.remove(3)

        assertFalse(bst.contains(3))
        assert(bst.size == 2)
        assert(bst.root!!.right!!.value == 2)
    }

    @Test
    fun shouldMoveRightChildUp() {
        val bst = KtBinarySearchTree<Int>()
        bst.add(1)
        bst.add(2)
        bst.add(3)

        bst.remove(2)

        assertFalse(bst.contains(2))
        assert(bst.size == 2)
        assert(bst.root!!.right!!.value == 3)
    }

    @Test
    fun shouldReassignChild() {
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

        bst.remove(3)

        assertFalse(bst.contains(3))
        assert(bst.size == 4)
        assert(bst.root!!.left!!.value == 4)
        assert(bst.root!!.left!!.left!!.value == 2)
    }

    @Test
    fun shouldReassignChildTwo() {
        /*
                 5
                /\
               3  7
                  /\
                 6  9
        */
        val bst = KtBinarySearchTree<Int>()
        bst.add(5)
        bst.add(3)
        bst.add(7)
        bst.add(6)
        bst.add(9)

        bst.remove(7)

        assertFalse(bst.contains(7))
        assert(bst.size == 4)
        assert(bst.root!!.right!!.value == 9)
        assert(bst.root!!.right!!.left!!.value == 6)
    }

    @Test
    fun testStructure() {
        /*
                1
                 \
                  3
                 /
                2
         */
        val bst = KtBinarySearchTree<Int>()
        bst.add(1)
        bst.add(3)
        bst.add(2)

        assert(bst.root!!.value == 1)
        assert(bst.root!!.right!!.value == 3)
        assert(bst.root!!.right!!.left!!.value == 2)
    }
}