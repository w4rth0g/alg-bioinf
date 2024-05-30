package pl.bioinf

import org.apache.commons.text.similarity.LevenshteinDistance

fun main() {

    testErrors("N")
    testErrors("P")
    testK()
    testDumb()

}

fun testDumb() {
    val header = listOf("10", "20", "30", "40", "50")

    val finalDistances = mutableListOf<Int>()

    (10..50 step 10).forEach { dumbP ->
        val spectrumList = readJsonFromFile("testInst/test_dumb.json")

        val distances = mutableListOf<Int>()
        (0..<60).forEach {
            val nodes = InstGenerator.generateInstance(
                spectrumList.spectrumList[it].first,
                spectrumList.spectrumList[it].sectrum
            )

            val reconstruction = DNAReconstruction()
            val reconstructedDNA = reconstruction.reconstructDNA(
                nodes, spectrumList.spectrumList[it].dnaStr.length, dumbP)

            val levenshteinDistance = LevenshteinDistance()
            val distance = levenshteinDistance.apply(spectrumList.spectrumList[it].dnaStr, reconstructedDNA)

            distances.add(distance)
        }

        finalDistances.add(distances.sum() / distances.size)
    }

    println(finalDistances)
    writeCsvFile("test_dumb.csv", finalDistances, header)
}

fun testK() {
    val header = listOf("6", "7", "8", "9", "10")

    val finalDistances = mutableListOf<Int>()

    (6..10).forEach { kNum ->
        val spectrumList = readJsonFromFile("testInst/test2_k_$kNum.json")

        val distances = mutableListOf<Int>()
        (0..<20).forEach {
            val nodes = InstGenerator.generateInstance(
                spectrumList.spectrumList[it].first,
                spectrumList.spectrumList[it].sectrum
            )

            val reconstruction = DNAReconstruction()
            val reconstructedDNA = reconstruction.reconstructDNA(
                nodes, spectrumList.spectrumList[it].dnaStr.length, 0)

            val levenshteinDistance = LevenshteinDistance()
            val distance = levenshteinDistance.apply(spectrumList.spectrumList[it].dnaStr, reconstructedDNA)

            distances.add(distance)
        }

        finalDistances.add(distances.sum() / distances.size)
    }

    println(finalDistances)
    writeCsvFile("test2_k.csv", finalDistances, header)
}

fun testErrors(errorType: String) {
    val sequence = if (errorType == "N") (2..10 step 2) else (3..15 step 3)

    val header = if (errorType == "N")
        listOf("2%", "4%", "6%", "8%", "10%")
    else listOf("3%", "6%", "9%", "12%", "15%")

    val finalDistances = mutableListOf<Int>()

    val fileNT = if (errorType == "N") "negative" else "positive"

    sequence.forEach { errorRate ->
        val spectrumList = readJsonFromFile("testInst/test2_${fileNT}_$errorRate.json")

        val distances = mutableListOf<Int>()
        (0..<20).forEach {
            val nodes = InstGenerator.generateInstance(
                spectrumList.spectrumList[it].first,
                spectrumList.spectrumList[it].sectrum
            )

            val reconstruction = DNAReconstruction()
            val reconstructedDNA = reconstruction.reconstructDNA(
                nodes, spectrumList.spectrumList[it].dnaStr.length,0)

            val levenshteinDistance = LevenshteinDistance()
            val distance = levenshteinDistance.apply(spectrumList.spectrumList[it].dnaStr, reconstructedDNA)

            distances.add(distance)
        }

        finalDistances.add(distances.sum() / distances.size)
    }

    println(finalDistances)
    writeCsvFile("test2_$fileNT.csv", finalDistances, header)
}