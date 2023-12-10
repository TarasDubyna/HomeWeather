package taras.du.data.data_sourse.device

import kotlinx.coroutines.flow.SharedFlow
import taras.du.bluetooth.model.DeviceSettings
import taras.du.domain.model.tick.Tick
import java.util.concurrent.Flow

interface DeviceDataSource {

    suspend fun getDeviceSettings(): Flow<DeviceSettings>
    suspend fun setDeviceSettings(): Flow<DeviceSettings>
    suspend fun getStoredTicks(): SharedFlow<List<Tick>>
}