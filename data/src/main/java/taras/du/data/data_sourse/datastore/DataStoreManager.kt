package taras.du.data.data_sourse.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context): DeviceDataStore {

    private val TAG = "DeviceInfoDataStore"

    companion object {
        private const val DATA_STORE_NAME = "DEVICE_INFO_PREFERENCES"

        val LAST_SYNC_KEY = longPreferencesKey("LAST_SYNC_KEY")
        val DEVICE_ADDRESS_KEY = stringPreferencesKey("DEVICE_ADDRESS_KEY")
        val STORAGE_TOTAL_KEY = intPreferencesKey("STORAGE_TOTAL_KEY")
        val STORAGE_FREE_KEY = intPreferencesKey("STORAGE_FREE_KEY")
        val TICK_FREQUENCY_KEY = intPreferencesKey("TICK_FREQUENCY_KEY")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            DATA_STORE_NAME)
    }

    private val _deviceAddress: MutableSharedFlow<String?> = MutableSharedFlow()
    override val deviceAddress: SharedFlow<String?> = _deviceAddress
    private val _lastSyncTime: MutableSharedFlow<Date?> = MutableSharedFlow()
    override val lastSyncTime: SharedFlow<Date?> = _lastSyncTime
    private val _measurementFrequency: MutableSharedFlow<Int> = MutableSharedFlow()
    override val measurementFrequency: SharedFlow<Int> = _measurementFrequency
    private val _storageTotalSpace: MutableSharedFlow<Int> = MutableSharedFlow()
    override val storageTotalSpace: SharedFlow<Int> = _storageTotalSpace
    private val _storageFreeSpace: MutableSharedFlow<Int> = MutableSharedFlow()
    override val storageFreeSpace: SharedFlow<Int> = _storageFreeSpace

    override suspend fun saveDeviceAddress(address: String) {
        context.dataStore.edit {
            it[DEVICE_ADDRESS_KEY] = address
            _deviceAddress.tryEmit(it[DEVICE_ADDRESS_KEY])
        }
    }

    override suspend fun clearDeviceAddress() {
        context.dataStore.edit {
            it[DEVICE_ADDRESS_KEY] = ""
            _deviceAddress.tryEmit(it[DEVICE_ADDRESS_KEY])
        }
    }

    override suspend fun updateLastSyncTime(date: Date) {
        context.dataStore.edit {
            it[LAST_SYNC_KEY] = date.time
            it[LAST_SYNC_KEY]?.let { time -> _lastSyncTime.tryEmit(Date(time)) } ?: _lastSyncTime.tryEmit(null)
        }
    }

    override suspend fun updateStorageTotalSpace(totalSpace: Int) {
        context.dataStore.edit {
            it[STORAGE_TOTAL_KEY] = totalSpace
            _storageTotalSpace.tryEmit(it[STORAGE_TOTAL_KEY] ?: -1)
        }
    }

    override suspend fun updateStorageFreeSpace(freeSpace: Int) {
        context.dataStore.edit {
            it[STORAGE_FREE_KEY] = freeSpace
            _storageFreeSpace.tryEmit(it[STORAGE_FREE_KEY] ?: -1)
        }
    }

}
