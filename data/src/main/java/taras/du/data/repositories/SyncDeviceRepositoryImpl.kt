package taras.du.data.repositories

import kotlinx.coroutines.flow.Flow
import taras.du.domain.model.DeviceParam
import taras.du.domain.model.ParamValue
import taras.du.domain.repository.SyncDeviceRepository
import javax.inject.Singleton


@Singleton
class SyncDeviceRepositoryImpl: SyncDeviceRepository {
    override suspend fun syncTicks(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun syncDeviceInfo(): Flow<Map<DeviceParam, ParamValue>> {
        TODO("Not yet implemented")
    }
}