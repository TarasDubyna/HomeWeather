package taras.du.data.data_sourse.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TickDao {

    /*
    @Query("SELECT * FROM ticks")
    fun getAllTicks(): Flow<List<TickEntity>>

    @Query("SELECT * FROM ticks WHERE type=:measurementType")
    fun getTicks(measurementType: String): Flow<List<TickEntity>>

    @Query("SELECT * FROM ticks WHERE ts BETWEEN :fromDate AND :toDate")
    fun getTicks(fromDate: Long, toDate: Long): Flow<List<TickEntity>>
    */

    @Query("SELECT * FROM ticks")
    fun getAllTicks(): Flow<List<TickEntity>>

    @Query("SELECT * FROM ticks WHERE type=:measurementType AND ts BETWEEN :fromDate AND :toDate")
    fun getTicks(measurementType: String, fromDate: Long, toDate: Long): Flow<List<TickEntity>>

    @Query("SELECT * FROM ticks WHERE ts BETWEEN :fromDate AND :toDate")
    fun getTicks(fromDate: Long, toDate: Long): Flow<List<TickEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTick(tick: TickEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTicks(ticks: List<TickEntity>)

    @Query("DELETE FROM ticks")
    suspend fun deleteAll()

}