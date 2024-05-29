package pl.bioinf

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class SpectrumObj(val first: String, val sectrum: Array<String>)

@Serializable
data class SpectrumObjList(val spectrumList: List<SpectrumObj>)

fun writeJsonToFile(spectrum: SpectrumObjList, fileName: String) {
    val directory = File("testInst")
    if (!directory.exists()) {
        directory.mkdirs() // Create the directory if it doesn't exist
    }
    val jsonString = Json.encodeToString(spectrum)
    File("testInst/$fileName").writeText(jsonString)
}

fun readJsonFromFile(fileName: String): SpectrumObjList {
    val jsonString = File(fileName).readText()
    return Json.decodeFromString(jsonString)
}