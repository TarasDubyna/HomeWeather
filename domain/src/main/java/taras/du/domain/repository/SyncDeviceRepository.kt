package taras.du.domain.repository

import kotlinx.coroutines.flow.StateFlow
import taras.du.domain.model.device.SyncState

interface SyncDeviceRepository {
    suspend fun syncStoredTicks(): StateFlow<SyncState>
    suspend fun syncDeviceSettings(): StateFlow<SyncState>
    //suspend fun syncDeviceInfo(): Flow<Map<Param, ParamValue>>
}