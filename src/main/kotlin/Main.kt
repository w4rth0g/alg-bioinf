package pl.bioinf

import pl.bioinf.data.DNA_STR

fun main() {

    val dnaStr = InstGenerator.generateRandomDNASequence(100)
//    val nPositive = 10
//    val nNegative = 8
    val kNum = 8
//    val dnaStr = DNA_STR

    println("Original DNA Sequence: $dnaStr")

    val nodes = InstGenerator.generateInstance(dnaStr, kNum, 0, 0)

    println("First node: ${nodes.firstNode}")
    println("All nodes: ${nodes.allNodes}")

    val reconstruction = DNAReconstruction()
    val reconstructedDNA = reconstruction.reconstructDNA(nodes, kNum)

    println("Reconstructed DNA sequence: $reconstructedDNA")

    println("Equal: ${reconstructedDNA.contains(dnaStr)}")
}