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

    (6..10).forEach { kNum ->
        val specList = mutableListOf<SpectrumObj>()
        (0..<20).forEach {
            val dnaString = InstGenerator.generateRandomDNASequence(nn)
            val (first, spectrum) = InstGenerator.generateSpectrum(dnaString, kNum, 0, 0)

            val specJson = SpectrumObj(dnaString, first, spectrum)
            specList.add(specJson)
        }
        val spectrumObjList = SpectrumObjList(specList)
        writeJsonToFile(spectrumObjList, "test2_k_$kNum.json")
    }

    val specList = mutableListOf<SpectrumObj>()
    (0..<20).forEach {
        val dnaString = InstGenerator.generateRandomDNASequence(nn)
        val nPosErrors: Int = (nn * 0.12).toInt()
        val (first, spectrum) = InstGenerator.generateSpectrum(dnaString, k, nPosErrors, 0)

        val specJson = SpectrumObj(dnaString, first, spectrum)
        specList.add(specJson)
    }
    (0..<20).forEach {
        val dnaString = InstGenerator.generateRandomDNASequence(nn)
        val nNegErrors: Int = (nn * 0.08).toInt()
        val (first, spectrum) = InstGenerator.generateSpectrum(dnaString, k, 0, nNegErrors)

        val specJson = SpectrumObj(dnaString, first, spectrum)
        specList.add(specJson)
    }
    (0..<20).forEach {
        val dnaString = InstGenerator.generateRandomDNASequence(nn)
        val nErrors: Int = (nn * 0.1).toInt()
        val (first, spectrum) = InstGenerator.generateSpectrum(dnaString, k, nErrors, nErrors)

        val specJson = SpectrumObj(dnaString, first, spectrum)
        specList.add(specJson)
    }
    val spectrumObjList = SpectrumObjList(specList)
    writeJsonToFile(spectrumObjList, "test_dumb.json")

}