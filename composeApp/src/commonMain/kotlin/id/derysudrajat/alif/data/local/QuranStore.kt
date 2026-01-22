package id.derysudrajat.alif.data.local

import alifmuslimapp.composeapp.generated.resources.Res
import id.derysudrajat.alif.data.remote.response.JuzResponse
import id.derysudrajat.alif.data.remote.response.ListJuzResponse
import id.derysudrajat.alif.data.remote.response.ListSurahResponse
import id.derysudrajat.alif.data.remote.response.SurahResponse
import kotlinx.serialization.json.Json

class QuranStore {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getSurahList(): List<SurahResponse> {
        val bytes = Res.readBytes("files/surah.json")
        val jsonString = bytes.decodeToString()
        val response = json.decodeFromString<ListSurahResponse>(jsonString)
        return response.listSurahResponse ?: emptyList()
    }

    suspend fun getJuzList(): List<JuzResponse> {
        val bytes = Res.readBytes("files/juz.json")
        val jsonString = bytes.decodeToString()
        val response = json.decodeFromString<ListJuzResponse>(jsonString)
        return response.data ?: emptyList()
    }
}