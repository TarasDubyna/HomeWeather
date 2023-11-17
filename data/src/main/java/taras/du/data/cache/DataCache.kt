package taras.du.data.cache

import taras.du.bluetooth.model.data.DeviceData
import taras.du.data.data_sourse.database.TickDao
import javax.inject.Inject

class DataCache @Inject constructor(
    private val tickDao: TickDao
): BluetoothDataCache {

    private val TAG = "DataCache"

    override suspend fun cacheReceivedData(deviceData: DeviceData) {
        TODO("Not yet implemented")
    }


}