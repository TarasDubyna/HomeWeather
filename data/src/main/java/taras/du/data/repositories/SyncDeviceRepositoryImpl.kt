package taras.du.data.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import taras.du.data.data_sourse.database.TickDao
import taras.du.data.data_sourse.datastore.DeviceParamsDataStore
import taras.du.data.data_sourse.device.DeviceDataSource
import taras.du.domain.model.DeviceParam
import taras.du.domain.model.ParamValue
import taras.du.domain.model.device.SyncState
import taras.du.domain.repository.SyncDeviceRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SyncDeviceRepositoryImpl @Inject constructor(
    private val deviceDataSource: DeviceDataSource,
    private val tickDao: TickDao,
    private val deviceParamsDataStore: DeviceParamsDataStore
): SyncDeviceRepository {

    override suspend fun syncStoredTicks(): StateFlow<SyncState> {
        TODO("Not yet implemented")
    }

    override suspend fun syncDeviceSettings(): StateFlow<SyncState> {
        TODO("Not yet implemented")
    }

    private fun sendDeviceMessage(messageType: String, body: String) {
        TODO("Not yet implemented")
    }


}