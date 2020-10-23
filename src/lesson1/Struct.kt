package lesson1

class Struct(
    val a: Int,
    val b: Int
)
//) : Comparable<Struct> {
//    override fun compareTo(other: Struct): Int {
//        val comparedA = a.compareTo(other.a)
//        return if (comparedA != 0) {
//            +comparedA
//        } else {
//            b.compareTo(other.b)
//        }
//    }
//
//    override fun toString(): String {
//        return "($a $b)"
//    }
//}

val structs = mutableListOf(Struct(2, 3), Struct(1, 6), Struct(3, 5), Struct(2, 2))

fun main() {
    println(structs)
    structs.sortBy { struct -> struct.a }
    print(structs)
}
