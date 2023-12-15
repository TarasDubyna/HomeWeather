package taras.du.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import taras.du.domain.model.device.DeviceSettings
import taras.du.domain.model.device.DeviceState
import java.util.Date

interface DeviceRepository {
    fun deviceState(): StateFlow<DeviceState>
    suspend fun getDeviceSettings(): Flow<DeviceSettings>
    suspend fun updateDeviceSettings(deviceSettings: DeviceSettings): Flow<DeviceSettings>
    suspend fun restartDevice(): Flow<Boolean>
}