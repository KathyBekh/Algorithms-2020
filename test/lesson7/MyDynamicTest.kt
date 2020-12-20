package lesson7

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MyDynamicTest {
    @Test
    fun foundSubsequence() {
        val subsequence = longestCommonSubSequence("Hello world!", "World Hello!")
        assert("Hello!" == subsequence)
    }

    @Test
    fun doNotFoundSubsequence() {
        val subsequence = longestCommonSubSequence("привет", "World Hello!")
        assert("" == subsequence)
    }

    @Test
    fun doNotFoundSubsequenceRegister() {
        val subsequence = longestCommonSubSequence("wORLD hELLO!", "World Hello!")
        assert(" !" == subsequence)
    }

    @Test
    fun foundSubsequenceWithEmptyString() {
        val subsequence = longestCommonSubSequence(
            "", "hello!"
        )
        println(subsequence)
        assert("" == subsequence)
    }

    @Test
    fun foundSubsequenceBad() {
        val subsequence = longestCommonSubSequence(
            "Будем рассматривать самый общий случай — случай ориентированного мультиграфа, возможно, с петлями. " +
                    "Также мы предполагаем, что эйлеров цикл в графе существует (и состоит хотя бы из одной вершины). " +
                    "Для поиска эйлерова цикла воспользуемся тем, что эйлеров цикл — это объединение всех простых циклов графа. " +
                    "Следовательно, наша задача — эффективно найти все циклы и эффективно объединить их в один. ",
            "один"
        )
        println(subsequence)
        assert("один" == subsequence)
    }

    @Test
    fun testLongestSubsequence() {
        assertEquals(listOf(10), longestIncreasingSubSequence(listOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)))
    }

    @Test
    fun riseLow() {
        assertEquals(listOf(5, 6, 11, 13, 29, 100), longestIncreasingSubSequence(listOf(10, 9, 8, 7, 6, 5, 6, 11, 13, 29, 100)))
    }

}