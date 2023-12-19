package taras.du.data.data_sourse.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticks")
data class TickEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val uid: Long = 0,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "ts") val ts: Long,
    @ColumnInfo(name = "value") val value: Float,
)
