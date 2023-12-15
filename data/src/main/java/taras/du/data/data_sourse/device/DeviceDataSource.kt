package taras.du.data.data_sourse.device

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import taras.du.data.data_sourse.database.TickEntity
import taras.du.data.model.DeviceTicksModel
import taras.du.domain.model.device.DeviceState
import taras.du.domain.model.tick.Tick

interface DeviceDataSource {
    val deviceState: StateFlow<DeviceState>
    suspend fun getDeviceParams(params: Set<String>): Flow<Map<String,String>>
    suspend fun setDeviceParams(params: Map<String, String>): Flow<Map<String,String>>
    suspend fun getStoredTicks(): SharedFlow<List<DeviceTicksModel>>
}