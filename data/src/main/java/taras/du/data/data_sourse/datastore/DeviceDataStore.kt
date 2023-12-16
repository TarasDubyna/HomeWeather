package taras.du.data.data_sourse.datastore

import kotlinx.coroutines.flow.SharedFlow
import java.util.Date

interface DeviceDataStore {
    val deviceAddress: SharedFlow<String?>
    val lastSyncTime: SharedFlow<Date?>
    val measurementFrequency: SharedFlow<Int>
    val storageTotalSpace: SharedFlow<Int>
    val storageFreeSpace: SharedFlow<Int>

    suspend fun saveDeviceAddress(address: String)
    suspend fun clearDeviceAddress()
    suspend fun updateLastSyncTime(date: Date)
    suspend fun updateStorageTotalSpace(totalSpace: Int)
    suspend fun updateStorageFreeSpace(freeSpace: Int)
}