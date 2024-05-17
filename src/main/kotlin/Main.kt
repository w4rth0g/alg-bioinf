package pl.bioinf

fun main() {

    val dnaStr = InstGenerator.generateRandomDNASequence(500)
    val nPositive = 10
    val nNegative = 8
    val kNum = 9

    println(dnaStr)

    val nodes = InstGenerator.generateInstance(dnaStr, kNum, nPositive, nNegative)

    println(nodes)
}