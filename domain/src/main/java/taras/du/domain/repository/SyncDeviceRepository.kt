package taras.du.domain.repository

import kotlinx.coroutines.flow.Flow

interface SyncDeviceRepository {
    suspend fun syncTicks(): Flow<Boolean>
    //suspend fun syncDeviceInfo(): Flow<Map<Param, ParamValue>>
}