package pl.bioinf

import org.apache.commons.text.similarity.LevenshteinDistance
import pl.bioinf.data.DNA_STR
import kotlin.random.Random

fun main() {

    val nPositive = 0
    val nNegative = 0
    val kNum = 9
    val nNukl = 700

    val dnaStr = InstGenerator.generateRandomDNASequence(nNukl)
//    val dnaStr = "GATCTAGTGCTAGTAGCGGGTTATATGCGTGAAGGAGACTTAACTACGGTGACGTAAGGTGGGATTGACATGGTCCGAGCATCTAGTAACGCGCCGGGTC"
//    val dnaStr = DNA_STR
//    val dnaStr = "GTGGTCGTCAGATTAGGGAATAAAACGTGCAGCGAGATACGAACGTTCGGGAGTTGATACCGAGTAGGGTGCAGCCCCGCGCGGTATCAATCCCGCATCA"

//    val dnaStr = DNA_STR

    println("Original DNA Sequence: $dnaStr")

    val (first, spectrum) = InstGenerator.generateSpectrum(dnaStr, kNum, nPositive, nNegative)
    val nodes = InstGenerator.generateInstance(first, spectrum)

    println("First node: ${nodes.first}")
//    println("All nodes: ${nodes.allNodes}")

    val reconstruction = DNAReconstruction()
    val reconstructedDNA = reconstruction.reconstructDNA(nodes, nNukl, 0)

    println("Reconstructed DNA sequence: $reconstructedDNA")

    val levenshteinDistance = LevenshteinDistance()
    val distance = levenshteinDistance.apply(dnaStr, reconstructedDNA)

    println("Miara lev: $distance")
}