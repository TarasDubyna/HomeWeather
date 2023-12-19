package taras.du.data.repositories

import android.bluetooth.BluetoothGattService
import android.content.Context
import androidx.core.content.getSystemService
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import taras.du.data.data_sourse.datastore.DeviceDataStore
import taras.du.domain.model.device.DeviceSettings
import taras.du.domain.model.device.DeviceState
import taras.du.domain.repository.DeviceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val dataStore: DeviceDataStore): DeviceRepository {


    private val _deviceState: MutableStateFlow<DeviceState> = MutableStateFlow(DeviceState.DISCONNECTED)


    override fun deviceState(): StateFlow<DeviceState> {
        when(BluetoothService.getDefaultInstance().status) {
            BluetoothStatus.CONNECTED -> _deviceState.tryEmit(DeviceState.CONNECTED)
            BluetoothStatus.CONNECTING -> _deviceState.tryEmit(DeviceState.CONNECTING)
            else -> {
                dataStore.deviceAddress.collectLatest {storedAddress -> storedAddress?.let { _deviceState.tryEmit }
                }
            }
        }
        return _deviceState.asStateFlow()
    }

    override suspend fun getDeviceSettings(): Flow<DeviceSettings> {
        TODO("Not yet implemented")
    }

    override suspend fun updateDeviceSettings(deviceSettings: DeviceSettings): Flow<DeviceSettings> {
        TODO("Not yet implemented")
    }

    override suspend fun restartDevice(): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}