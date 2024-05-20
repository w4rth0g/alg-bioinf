package pl.bioinf

fun main() {

    val dnaStr = InstGenerator.generateRandomDNASequence(500)
    val nPositive = 10
    val nNegative = 8
    val kNum = 9

    println("Original DNA Sequence: $dnaStr")

    val nodes = InstGenerator.generateInstance(dnaStr, kNum, nPositive, nNegative)

    val reconstruction = DNAReconstruction()
    val reconstructedDNA = reconstruction.reconstructDNA(nodes)

    println("Reconstructed DNA sequence: $reconstructedDNA")
}