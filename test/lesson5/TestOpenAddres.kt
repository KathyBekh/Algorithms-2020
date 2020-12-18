package lesson5

import org.junit.jupiter.api.Test

class TestOpenAddress {

    @Test
    fun findTestString() {
        val table = KtOpenAddressingSet<String>(4)
        table.add("one")
        table.add("two")
        table.add("three")
        table.add("four")
        println("find one: ${table.contains("one")}")
        println("find two: ${table.contains("two")}")
        println("find four: ${table.contains("four")}")
        println("find fife: ${table.contains("fife")}")
        println("find three: ${table.contains("three")}")

        println("remove two: ${table.remove("two")}")
        println("find two: ${table.contains("two")}")
        println("contains one: ${table.contains("one")}")
        println("contains three: ${table.contains("three")}")
        println("contains two: ${table.contains("two")}")
        table.add("two")
        println(table)
    }


    @Test
    fun findTestInt() {
        val table = KtOpenAddressingSet<Int>(4)
        table.add(1)
        table.add(2)
        table.add(3)
        table.add(4)
        println("find one: ${table.contains(1)}")
        println("find two: ${table.contains(2)}")
        println("find four: ${table.contains(4)}")
        println("find fife: ${table.contains(5)}")
        println("find three: ${table.contains(3)}")

        println("remove two: ${table.remove(2)}")
        println("find two: ${table.contains(2)}")
        println("contains one: ${table.contains(1)}")
        println("contains three: ${table.contains(3)}")
        println("contains two: ${table.contains(2)}")
        table.add(2)
        println(table)
    }
}