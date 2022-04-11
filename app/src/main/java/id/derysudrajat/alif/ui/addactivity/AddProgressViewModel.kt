package id.derysudrajat.alif.ui.addactivity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.derysudrajat.alif.data.model.ProgressTask
import id.derysudrajat.alif.repo.PrayerRepository
import id.derysudrajat.alif.service.PrayerAlarm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProgressViewModel @Inject constructor(
    private val repository: PrayerRepository,
    private val alarm: PrayerAlarm
) : ViewModel() {

    private val listActive = mutableListOf(-1, -1, -1, -1, -1, -1, -1)
    private val listTextRepeating = mutableListOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    var repeating = MutableStateFlow(listActive)
    var textRepeating = MutableStateFlow("Everyday")
    private val scope = viewModelScope

    fun setRepeating(position: Int, isActive: Boolean) {
        if (isActive) listActive[position] = -1 else listActive[position] = position
        scope.launch { repeating.emit(mutableListOf()) }
        scope.launch { repeating.emit(listActive) }
        scope.launch { textRepeating.emit("") }
        scope.launch { textRepeating.emit(getTextRepeating(listActive)) }
    }

    fun setRepeating(isChecked: Boolean) {
        repeat(7) {
            listActive[it] = if (isChecked) it else -1
        }.also {
            scope.launch { repeating.emit(mutableListOf()) }
            scope.launch { repeating.emit(listActive) }
            scope.launch { textRepeating.emit("") }
            scope.launch { textRepeating.emit(getTextRepeating(listActive)) }
        }
    }

    fun addTask(context: Context, task: ProgressTask) {
        task.repeating = getRepeatingTask()
        alarm.setActivityAlarm(context, task, true)
        scope.launch { repository.addProgressTask(task) }
    }

    private fun getRepeatingTask() = buildString {
        when (listActive.filter { it != -1 }.size) {
            7 -> append("7")
            0 -> append("-1")
            else -> listActive.forEachIndexed { index, i ->
                if (i != -1) {
                    append(i)
                    if (index != 6) append(" ")
                }
            }
        }
    }

    private fun getTextRepeating(listActive: MutableList<Int>) = buildString {
        if (listActive.contains(-1)) listActive.filter { it != -1 }.let { active ->
            active.forEachIndexed { index, i ->
                append(listTextRepeating[i])
                if (index != (active.size - 1)) append(", ")
            }
            if (active.isEmpty()) append("Not Repeating")
        } else append("Everyday")
    }
}