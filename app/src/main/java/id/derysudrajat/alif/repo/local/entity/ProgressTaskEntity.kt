package id.derysudrajat.alif.repo.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.derysudrajat.alif.data.model.ProgressTask

@Entity(tableName = "progress_task")
data class ProgressTaskEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "date_long")
    val dateLong: Long,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "repeating")
    val repeating: String
)

fun List<ProgressTaskEntity>.toProgressTask(listCheck: List<CheckedTaskEntity>): MutableList<ProgressTask> {
    val listTask = mutableListOf<ProgressTask>()
    this.forEach {
        listTask.add(ProgressTask(
            id = it.id,
            title = it.title,
            date = it.dateLong,
            repeating = it.repeating,
            isCheck = listCheck.isChecked(it.id)
        ))
    }
    return listTask
}