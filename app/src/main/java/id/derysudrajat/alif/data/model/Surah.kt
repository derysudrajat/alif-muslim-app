package id.derysudrajat.alif.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Surah(
    val name: String,
    val ayahs: Int,
    val number: Int,
    val mean: String,
    val arabic: String,
    val audioUrl: String,
    val type: String,
    val index: Int
) : Parcelable {
    companion object {
        val empty = Surah(
            "", 0, 0, "", "", "", "", 0
        )
    }
}