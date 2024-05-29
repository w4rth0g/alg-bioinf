package pl.bioinf

import org.apache.commons.text.similarity.LevenshteinDistance

fun main() {

    testErrors("N")
    testErrors("P")

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
                nodes, spectrumList.spectrumList[it].dnaStr.length)

            val levenshteinDistance = LevenshteinDistance()
            val distance = levenshteinDistance.apply(spectrumList.spectrumList[it].dnaStr, reconstructedDNA)

            distances.add(distance)
        }

        finalDistances.add(distances.sum() / distances.size)
    }

    println(finalDistances)
    writeCsvFile("test2_$fileNT.csv", finalDistances, header)
}