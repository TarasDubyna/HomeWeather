package taras.du.domain.repository

import kotlinx.coroutines.flow.Flow
import taras.du.domain.model.tick.MeasurementTick
import taras.du.domain.model.MeasurementType
import taras.du.domain.model.tick.Tick
import java.util.Date

interface TickRepository {

    suspend fun getTicks(
        vararg measurementType: MeasurementType,
        from: Date,
        to: Date
    ): Flow<Map<MeasurementType, List<Tick>>>

    suspend fun getLastTicks(vararg measurementType: MeasurementType): Flow<List<MeasurementTick>>

    suspend fun getTicksBounds(
        vararg measurementType: MeasurementType,
        from: Date,
        to: Date
    ): Flow<Map<MeasurementType, Pair<Tick, Tick>>>

    suspend fun startStreamRealTicks(): Flow<List<MeasurementTick>>

    suspend fun stopStreamRealTicks()

}
