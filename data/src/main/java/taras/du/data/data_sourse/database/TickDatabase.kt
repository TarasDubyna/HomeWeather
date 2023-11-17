package taras.du.data.data_sourse.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TickEntity::class], version = 1, exportSchema = false)
abstract class TickDatabase : RoomDatabase() {
    abstract fun tickDao(): TickDao
}