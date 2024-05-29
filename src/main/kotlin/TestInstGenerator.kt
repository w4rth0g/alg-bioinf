package pl.bioinf

fun main() {
    val nn = 600
    val k = 10

    (2..10 step 2).forEach { errorRate ->
        val specList = mutableListOf<SpectrumObj>()
        (0..<20).forEach {
            val dnaString = InstGenerator.generateRandomDNASequence(nn)
            val nNegErrors: Int = (nn * (errorRate / 100.0)).toInt()
            val (first, spectrum) = InstGenerator.generateSpectrum(dnaString, k, 0, nNegErrors)

            val specJson = SpectrumObj(dnaString, first, spectrum)
            specList.add(specJson)
        }
        val spectrumObjList = SpectrumObjList(specList)
        writeJsonToFile(spectrumObjList, "test2_negative_$errorRate.json")
    }

    (3..15 step 3).forEach { errorRate ->
        val specList = mutableListOf<SpectrumObj>()
        (0..<20).forEach {
            val dnaString = InstGenerator.generateRandomDNASequence(nn)
            val nPosErrors: Int = (nn * (errorRate / 100.0)).toInt()
            val (first, spectrum) = InstGenerator.generateSpectrum(dnaString, k, nPosErrors, 0)

            val specJson = SpectrumObj(dnaString, first, spectrum)
            specList.add(specJson)
        }
        val spectrumObjList = SpectrumObjList(specList)
        writeJsonToFile(spectrumObjList, "test2_positive_$errorRate.json")
    }
}