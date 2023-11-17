package taras.du.data.data_sourse.datastore

import android.content.Context
import androidx.core.content.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Date
import javax.inject.Inject


class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val TAG = "DeviceInfoDataStore"

    private val Context.dataStore by preferencesDataStore("settings")

    private val sharedPreferences = appContext.getSharedPreferences(DataStoreConstants.DEVICE_INFO_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun updateLastSyncTime(date: Date) {
        sharedPreferences.edit {
            putLong(DataStoreConstants.LAST_SYNC_KEY, date.time)
        }
    }
    fun updateStorageTotalSpace(space: Int) {
        sharedPreferences.edit {
            putInt(DataStoreConstants.STORAGE_TOTAL_KEY, space)
        }
    }
    fun updateStorageFreeSpace(space: Int) {
        sharedPreferences.edit {
            putInt(DataStoreConstants.STORAGE_FREE_KEY, space)
        }
    }
    fun updateTickFrequency(frequency: Int) {
        sharedPreferences.edit {
            putInt(DataStoreConstants.TICK_FREQUENCY_KEY, frequency)
        }
    }

}
