package pl.bioinf

import pl.bioinf.data.DNA_STR

fun main() {

    val dnaStr = InstGenerator.generateRandomDNASequence(100)
    val nPositive = 0
    val nNegative = 0
    val kNum = 8
//    val dnaStr = DNA_STR
//    val dnaStr = "GTGGTCGTCAGATTAGGGAATAAAACGTGCAGCGAGATACGAACGTTCGGGAGTTGATACCGAGTAGGGTGCAGCCCCGCGCGGTATCAATCCCGCATCA"

//    val dnaStr = DNA_STR

    println("Original DNA Sequence: $dnaStr")

    val nodes = InstGenerator.generateInstance(dnaStr, kNum, nPositive, nNegative)

    println("First node: ${nodes.first}")
//    println("All nodes: ${nodes.allNodes}")

    val reconstruction = DNAReconstruction()
    val reconstructedDNA = reconstruction.reconstructDNA(nodes, kNum)

    println("Reconstructed DNA sequence: $reconstructedDNA")

    println("Equal: ${reconstructedDNA.contains(dnaStr)}")
}