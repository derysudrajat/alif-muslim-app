package id.derysudrajat.alif.repo

import android.util.Log
import com.google.firebase.Timestamp
import id.derysudrajat.alif.data.model.*
import id.derysudrajat.alif.data.repository.DataRepositoryImpl
import id.derysudrajat.alif.repo.local.LocalDataSource
import id.derysudrajat.alif.repo.local.entity.toPayerReminders
import id.derysudrajat.alif.repo.local.entity.toProgressTask
import id.derysudrajat.alif.repo.remote.RemoteDataSource
import id.derysudrajat.alif.utils.TimeUtils.formatDate
import id.derysudrajat.alif.utils.TimeUtils.indexOfDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrayerRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : DataRepositoryImpl {
    override suspend fun getSchedule(
        lat: Double, long: Double, month: Int, year: Int
    ): Flow<States<List<Schedule>>> = remoteDataSource.getSchedule(lat, long, month, year)

    override suspend fun getAllReminder(): Flow<List<PrayerReminder>> =
        localDataSource.getAllReminder().map { it.toPayerReminders() }

    override suspend fun addAllReminders(listOfReminder: List<PrayerReminder>) =
        localDataSource.addAllReminder(listOfReminder.toReminderEntities())

    override suspend fun updateReminder(prayerReminder: PrayerReminder) =
        localDataSource.updateReminder(prayerReminder.toReminderEntity())

    override suspend fun deleteAllReminder() = localDataSource.deleteAllReminder()

    override suspend fun getProgressTask(date: String): Flow<List<ProgressTask>> = flow {
        localDataSource.getCheckedTask(date).collect { checkedTask ->
            localDataSource.getAllProgressTask().map { entities ->
                entities.filter { it.repeating.contains(Regex("([$indexOfDay,7,-1])")) }
                    .filter { if (!it.repeating.contains("7")) it.date == Timestamp.now().formatDate else it.title.isNotBlank() }
                    .sortedBy { it.date }.toProgressTask(checkedTask)
            }.collect { emit(it) }
        }
    }

    override suspend fun addProgressTask(task: ProgressTask) {
        localDataSource.addProgressTask(task.toProgressEntity())
        localDataSource.addCheckedTask(task.toCheckedEntity())
    }

    override suspend fun deleteProgressTask(task: ProgressTask) {
        localDataSource.deleteProgressTask(task.toProgressEntity())
        localDataSource.deleteCheckedTask(task.toCheckedEntity())
    }

    override suspend fun updateCheckedTask(task: ProgressTask) {
        Log.d("TAG", "updateCheckedTask: ${task.toCheckedEntity()}")
        localDataSource.updateCheckedTask(task.toCheckedEntity())
    }

}
