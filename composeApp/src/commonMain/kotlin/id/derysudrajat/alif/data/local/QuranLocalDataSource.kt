package id.derysudrajat.alif.data.local

import id.derysudrajat.alif.data.remote.response.toListJuz
import id.derysudrajat.alif.data.remote.response.toListSurah
import id.derysudrajat.alif.domain.model.JuzData
import id.derysudrajat.alif.domain.model.SurahData

class QuranLocalDataSource(
    private val quranStore: QuranStore
) {
    suspend fun getListSurah(): List<SurahData> {
        return quranStore.getSurahList().toListSurah()
    }

    suspend fun getListJuz(): List<JuzData> {
        return quranStore.getJuzList().toListJuz()
    }
}