package pl.bioinf

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class SpectrumObj(val dnaStr: String, val first: String, val sectrum: Array<String>)

@Serializable
data class SpectrumObjList(val spectrumList: List<SpectrumObj>)

fun writeJsonToFile(spectrum: SpectrumObjList, fileName: String) {
    val directory = File("testInst")
    if (!directory.exists()) {
        directory.mkdirs()
    }
    val jsonString = Json.encodeToString(spectrum)
    File("testInst/$fileName").writeText(jsonString)
}

fun readJsonFromFile(fileName: String): SpectrumObjList {
    val jsonString = File(fileName).readText()
    return Json.decodeFromString(jsonString)
}

fun writeCsvFile(fileName: String, data: List<Int>, header: List<String>) {
    val directory = File("testCsv")
    if (!directory.exists()) {
        directory.mkdirs()
    }

    val file = File("testCsv/$fileName")
    file.printWriter().use { out ->
        out.println(header.joinToString(separator = ";"))
        out.println(data.joinToString(separator = ";"))
    }
}