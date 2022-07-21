package id.derysudrajat.alif.ui.quran

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import id.derysudrajat.alif.R
import id.derysudrajat.alif.data.model.Ayah
import id.derysudrajat.alif.data.model.Juz
import id.derysudrajat.alif.data.model.Surah
import id.derysudrajat.alif.repo.PrayerRepository
import id.derysudrajat.alif.repo.States
import id.derysudrajat.alif.repo.remote.response.ListJuzResponse
import id.derysudrajat.alif.repo.remote.response.ListSurahResponse
import id.derysudrajat.alif.repo.remote.response.toListJuz
import id.derysudrajat.alif.repo.remote.response.toListSurah
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class QuranViewModel @Inject constructor(
    private val repository: PrayerRepository
) : ViewModel() {

    private var _listSurah by mutableStateOf(emptyList<Surah>())
    val listSurah get() = _listSurah

    private var _listJuz by mutableStateOf(emptyList<Juz>())
    val listJuz get() = _listJuz
    var listAyah by mutableStateOf(emptyList<Ayah>())

    fun getListSurah(activity: Activity) {
        viewModelScope.launch {
            val json = InputStreamReader(activity.resources.openRawResource(R.raw.surah))
            val response = Gson().fromJson(json, ListSurahResponse::class.java)
            response.listSurahResponse?.let {
                _listSurah = it.toListSurah()
            }
        }
    }

    fun getListJuz(activity: Activity) {
        viewModelScope.launch {
            val json = InputStreamReader(activity.resources.openRawResource(R.raw.juz))
            val response = Gson().fromJson(json, ListJuzResponse::class.java)
            response.data?.let {
                _listJuz = it.toListJuz()
            }
        }
    }

    fun getAyahFromSurah(noSurah: Int) {
        viewModelScope.launch {
            repository.getAyahQuran(noSurah).collect {
                if (it is States.Success) {
                    Log.d("TAG", "getAyahFromSurah: ${it.data}")
                    listAyah = it.data
                }
            }
        }
    }
}