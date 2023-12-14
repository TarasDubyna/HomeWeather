package taras.du.domain.repository

import kotlinx.coroutines.flow.Flow
import taras.du.domain.model.device.DeviceSettings
import java.util.Date

interface DeviceSettingsRepository {
    suspend fun getDeviceSettings(): Flow<DeviceSettings>
    suspend fun setTime(date: Date): Flow<DeviceSettings>
    suspend fun clearDeviceStorage(): Flow<Boolean>
    suspend fun restartDevice(): Flow<Boolean>
}