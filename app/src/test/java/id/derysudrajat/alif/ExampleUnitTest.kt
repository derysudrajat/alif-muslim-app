package id.derysudrajat.alif

import com.google.firebase.Timestamp
import id.derysudrajat.alif.data.model.Prayer
import id.derysudrajat.alif.data.model.TimingSchedule
import id.derysudrajat.alif.data.model.toList
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getHour() {
        val time = "18:05 (WIB)"
        assertEquals(18, time.split(":", " ").first().toInt())
    }

    @Test
    fun getNearestSchedule() {
        val timingSchedule = TimingSchedule(
            imsak = Prayer("04:42 (WIB)", false),
            fajr = Prayer("04:52 (WIB)", false),
            sunrise = Prayer("05:49 (WIB)", false),
            dhuhr = Prayer("11:52 (WIB)", false),
            asr = Prayer("15:06 (WIB)", false),
            maghrib = Prayer("17:54 (WIB)", false),
            isha = Prayer("18:51 (WIB)", false)
        )
        val hour = 19
        val min = 55
        timingSchedule.toList().let { listSchedule ->
            listSchedule.filter {
                it.time.split(":", " ").first().toInt() >= hour
            }.firstOrNull {
                if (it.time.split(":", " ").first().toInt() == hour) it.time.split(
                    ":", " "
                )[1].toInt() >= min
                else it.time.split(":", " ").first().toInt() >= hour
            }.let {
                if (it != null) println(it)
                else listSchedule.minByOrNull { sc -> sc.time.split(":", " ").first().toInt() }
                    ?.let { min ->
                        println(min)
                    }
            }
        }
    }

    @Test
    fun testInterval() {
        val interval = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 30)
        }.time.time - Timestamp.now().toDate().time

        println(interval)

        val timeUntil = Calendar.getInstance().apply { time = Date(interval) }
        println(timeUntil)
        println("${timeUntil.get(Calendar.HOUR_OF_DAY)}:${timeUntil.get(Calendar.MINUTE)}")
    }

    @Test
    fun testJuz() {
        var resultDegree = 339
        var finalDegree = 360 - resultDegree
        println(finalDegree)

        resultDegree = 0
        finalDegree = 360 - resultDegree
        println(finalDegree)
    }

    @Test
    fun testBismillah() {
        val ayah = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ"
        val bismillah = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ "
        println(ayah.contains(bismillah))
        println(ayah.split(bismillah).last())
    }

}