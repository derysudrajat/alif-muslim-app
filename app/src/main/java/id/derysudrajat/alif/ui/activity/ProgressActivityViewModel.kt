package id.derysudrajat.alif.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import id.derysudrajat.alif.data.model.ProgressTask
import id.derysudrajat.alif.repo.PrayerRepository
import id.derysudrajat.alif.utils.TimeUtils.formatDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressActivityViewModel @Inject constructor(
    val repository: PrayerRepository
) : ViewModel() {

    private val scope = viewModelScope
    val activities = MutableStateFlow<List<ProgressTask>>(listOf())
    private var currentActivity = listOf<ProgressTask>()

    fun getTodayActivity() = scope.launch {
        repository.getProgressTask(Timestamp.now().formatDate).collect { progress ->
            currentActivity = progress
            activities.emit(listOf())
            activities.emit(currentActivity)
        }
    }

    fun checkedTask(id: Long, isChecked: Boolean) {
        currentActivity.find { it.id == id }?.let {
            val task = it
            task.isCheck = isChecked
            scope.launch { repository.updateCheckedTask(task) }
                .also { getTodayActivity() }
        }
    }

    fun deleteTask(position: Int) {
        scope.launch { repository.deleteProgressTask(currentActivity[position]) }
            .also { getTodayActivity() }
    }
}