package taras.du.data.data_sourse.device

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import taras.du.data.data_sourse.database.TickEntity
import taras.du.domain.model.device.ArduinoSettings
import java.util.Date
import kotlin.time.Duration

interface ArduinoAPI {

    suspend fun getDeviceSettings(): Flow<ArduinoSettings>
    suspend fun setDeviceTime(time: Date)
    suspend fun setMeasurementFrequency(frequency: Duration)

    suspend fun restartDevice(): Boolean
    suspend fun clearSDCard(): Boolean

    suspend fun startStreamTicks(): SharedFlow<TickEntity>
    suspend fun stopStreamTicks()

    suspend fun startSyncTicks(): SharedFlow<TickEntity>
    suspend fun stopSyncTicks()

}
