package id.derysudrajat.alif.repo.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checked_task")
data class CheckedTaskEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "checked")
    val isChecked: Boolean,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "date_long")
    val dateLong : Long,
)

fun List<CheckedTaskEntity>.isChecked(id: Long): Boolean {
    val result = this.filter { it.id == id }
    return if (result.isEmpty()) false
    else result.first().isChecked
}
