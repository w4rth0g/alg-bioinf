package pl.bioinf

fun main() {
    val nn = 600
    val k = 10
    val dnaString = InstGenerator.generateRandomDNASequence(nn)

    (2..10 step 2).forEach { errorRate ->
        val specList = mutableListOf<SpectrumObj>()
        (0..<20).forEach {
            val nNegErrors: Int = (nn * (errorRate / 100.0)).toInt()
            val (first, spectrum) = InstGenerator.generateSpectrum(dnaString, k, 0, nNegErrors)

            val specJson = SpectrumObj(first, spectrum)
            specList.add(specJson)
        }
        val spectrumObjList = SpectrumObjList(specList)
        writeJsonToFile(spectrumObjList, "test2_$errorRate.json")
    }
}