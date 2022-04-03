package id.derysudrajat.alif.repo.local

import id.derysudrajat.alif.repo.local.entity.CheckedTaskEntity
import id.derysudrajat.alif.repo.local.entity.ProgressTaskEntity
import id.derysudrajat.alif.repo.local.entity.ReminderEntity
import id.derysudrajat.alif.repo.local.room.ReminderDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val reminderDao: ReminderDao
) {
    fun getAllReminder(): Flow<List<ReminderEntity>> = reminderDao.getAllReminder()
    suspend fun addAllReminder(reminders : List<ReminderEntity>) = reminderDao.addAllReminder(reminders)
    suspend fun updateReminder(reminder: ReminderEntity) = reminderDao.updateReminder(reminder)
    suspend fun deleteAllReminder() = reminderDao.deleteAll()

    fun getAllProgressTask(): Flow<List<ProgressTaskEntity>> = reminderDao.getProgressTask()
    suspend fun addProgressTask(task: ProgressTaskEntity) = reminderDao.addProgressTask(task)
    suspend fun deleteProgressTask(task: ProgressTaskEntity) = reminderDao.deleteProgressTask(task)

    fun getCheckedTask(date: String): Flow<List<CheckedTaskEntity>> = reminderDao.getCheckedTask(date)
    suspend fun addCheckedTask(task: CheckedTaskEntity) = reminderDao.addCheckedTask(task)
    suspend fun deleteCheckedTask(task: CheckedTaskEntity) = reminderDao.deleteCheckedTask(task)
    suspend fun updateCheckedTask(task: CheckedTaskEntity) = reminderDao.updateCheckedTask(task)
}