package id.derysudrajat.alif.ui.activity

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import id.derysudrajat.alif.data.model.ProgressTask
import id.derysudrajat.alif.repo.PrayerRepository
import id.derysudrajat.alif.repo.local.entity.CheckedTaskEntity
import id.derysudrajat.alif.service.PrayerAlarm
import id.derysudrajat.alif.utils.TimeUtils.formatDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressActivityViewModel @Inject constructor(
    val repository: PrayerRepository,
    private val alarm: PrayerAlarm
) : ViewModel() {

    private val scope = viewModelScope
    var activities by mutableStateOf(emptyList<ProgressTask>())
    val activitiesC = MutableStateFlow<List<ProgressTask>>(listOf())
    private var currentActivity = listOf<ProgressTask>()
    private var currentChecked = listOf<CheckedTaskEntity>()

    fun getTodayActivity() {
        scope.launch {
            repository.getProgressTask(Timestamp.now().formatDate).collect { progress ->
                currentActivity = progress
                activitiesC.emit(listOf())
                activitiesC.emit(currentActivity)
                activities = listOf()
                activities = currentActivity
            }
        }
        scope.launch {
            repository.getCheckedTask(Timestamp.now().formatDate).collect {
                currentChecked = it
            }
        }
    }

    fun checkedTask(id: Long, isChecked: Boolean) {
        currentActivity.find { it.id == id }?.let {
            val task = it
            task.isCheck = isChecked
            val isExist = currentChecked.find { c -> c.id == task.id } != null
            if (isExist) scope.launch {
                repository.updateCheckedTask(task) { getTodayActivity() }
            } else scope.launch {
                task.date = Timestamp.now().toDate().time
                repository.addCheckedTask(task) { getTodayActivity() }
            }
        }
    }

    fun deleteTask(context: Context, position: Int) {
        alarm.cancelActivityAlarm(context, currentActivity[position])
        scope.launch { repository.deleteProgressTask(currentActivity[position]) }
            .also { getTodayActivity() }
    }
}