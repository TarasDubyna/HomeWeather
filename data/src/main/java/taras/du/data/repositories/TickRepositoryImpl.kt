package taras.du.data.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import taras.du.data.data_sourse.database.TickDao
import taras.du.data.model.DeviceSettingsModel
import taras.du.domain.model.MeasurementTick
import taras.du.domain.model.MeasurementType
import taras.du.domain.model.Tick
import taras.du.domain.repository.TickRepository
import java.util.Date
import javax.inject.Inject

class TickRepositoryImpl @Inject constructor(
    private val tickDao: TickDao,
    private val dataStore: DataStore<DeviceSettingsModel>
) : TickRepository {

    private val TAG = "TickRepository"

    override suspend fun getTicks(
        vararg measurementType: MeasurementType,
        from: Date,
        to: Date
    ): Flow<Map<MeasurementType, List<Tick>>> = callbackFlow {
        tickDao.getAllTicks()
            .map { ticks -> ticks.map { it.toMeasurementTick() } }
            .collect {
                val tickMap =
                    it.filter { tick -> tick.time.before(to) && tick.time.after(from) }
                        .filter { tick -> measurementType.any { type -> type == tick.type } }
                        .groupBy { tick -> tick.type }
                Log.d(
                    TAG,
                    "getTicks: type=${measurementType.joinToString { type -> type.shortName }}, " +
                            "from=${from.toString()}, " +
                            "to=${to.toString()}, " +
                            "size=${it.size}"
                )
                trySend(tickMap)
            }
    }

    override suspend fun getLastTicks(vararg measurementType: MeasurementType): Flow<List<MeasurementTick>> =
        callbackFlow {
            tickDao.getAllTicks().map { ticks -> ticks.map { it.toMeasurementTick() } }
                .collect {
                    val tickMap =
                        it.filter { tick -> measurementType.any { type -> type == tick.type } }
                            .groupBy { tick -> tick.type }

                    val lastTicks = mutableListOf<MeasurementTick>()

                    measurementType.forEach { type ->
                        tickMap[type]?.maxByOrNull { tick -> tick.time }?.let { tick ->
                            lastTicks.add(tick)
                        }
                    }
                    Log.d(
                        TAG,
                        "getLastTicks: ${lastTicks.joinToString { tick -> tick.toString() }}"
                    )
                    trySend(lastTicks)
                }
        }

    override suspend fun getTicksBounds(
        vararg measurementType: MeasurementType,
        from: Date,
        to: Date
    ): Flow<Map<MeasurementType, Pair<Tick, Tick>>> = callbackFlow {
        tickDao.getAllTicks().map { ticks -> ticks.map { it.toMeasurementTick() } }
            .collect { ticks ->
                val bounds =
                    ticks.filter { it.time.before(to) && it.time.after(from) }
                        .filter { measurementType.any { type -> type == it.type } }
                        .groupBy { it.type }
                        .mapValues { value ->
                            val max = value.value.maxBy { tick -> tick.value }
                            val min = value.value.minBy { tick -> tick.value }
                            Pair(min, max)
                        }

                Log.d(TAG, "getTicksBounds: ${bounds.toString()}")
                trySend(bounds)
            }
    }

    override suspend fun startStreamRealTicks(): Flow<List<MeasurementTick>> {
        TODO("Not yet implemented")
    }

    override suspend fun stopStreamRealTicks() {
        TODO("Not yet implemented")
    }

}
