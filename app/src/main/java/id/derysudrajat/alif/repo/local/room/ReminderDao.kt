package id.derysudrajat.alif.repo.local.room

import androidx.room.*
import id.derysudrajat.alif.repo.local.entity.CheckedTaskEntity
import id.derysudrajat.alif.repo.local.entity.ProgressTaskEntity
import id.derysudrajat.alif.repo.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder")
    fun getAllReminder(): Flow<List<ReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllReminder(news: List<ReminderEntity>)

    @Update
    suspend fun updateReminder(news: ReminderEntity)

    @Query("DELETE FROM reminder")
    suspend fun deleteAll()

    @Query("SELECT * FROM progress_task")
    fun getProgressTask(): Flow<List<ProgressTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProgressTask(progressTaskEntity: ProgressTaskEntity)

    @Delete
    suspend fun deleteProgressTask(progressTaskEntity: ProgressTaskEntity)

    @Query("SELECT * FROM checked_task WHERE date = :date")
    fun getCheckedTask(date: String): Flow<List<CheckedTaskEntity>>

    @Update
    suspend fun updateCheckedTask(checkedTaskEntity: CheckedTaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCheckedTask(checkedTaskEntity: CheckedTaskEntity)

    @Delete
    suspend fun deleteCheckedTask(checkedTaskEntity: CheckedTaskEntity)
}