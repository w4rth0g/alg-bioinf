package pl.bioinf

import org.apache.commons.text.similarity.LevenshteinDistance

fun main() {
    val finalDistances = mutableListOf<Int>()

    (2..10 step 2).forEach { errorRate ->
        val spectrumList = readJsonFromFile("testInst/test2_$errorRate.json")

        val distances = mutableListOf<Int>()
        (0..<20).forEach {
            val nodes = InstGenerator.generateInstance(
                spectrumList.spectrumList[it].first,
                spectrumList.spectrumList[it].sectrum
            )

            val reconstruction = DNAReconstruction()
            val reconstructedDNA = reconstruction.reconstructDNA(
                nodes, spectrumList.spectrumList[it].dnaStr.length)

            val levenshteinDistance = LevenshteinDistance()
            val distance = levenshteinDistance.apply(spectrumList.spectrumList[it].dnaStr, reconstructedDNA)

            distances.add(distance)
        }

        finalDistances.add(distances.sum() / distances.size)
    }

    println(finalDistances)
    writeCsvFile("test2_negative.csv", finalDistances, listOf("2%", "4%", "6%", "8%", "10%"))
}