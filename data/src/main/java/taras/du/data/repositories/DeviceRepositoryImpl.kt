package taras.du.data.repositories

import android.content.Context
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import taras.du.domain.model.device.DeviceSettings
import taras.du.domain.model.device.DeviceState
import taras.du.domain.repository.DeviceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val dataStore: ): DeviceRepository {


    private val _deviceState: MutableStateFlow<DeviceState> = MutableStateFlow(DeviceState.NOT_CONNECTED)


    override fun deviceState(): StateFlow<DeviceState> {
        when(BluetoothService.getDefaultInstance().status) {
            BluetoothStatus.CONNECTED -> TODO()
            BluetoothStatus.CONNECTING -> TODO()
            BluetoothStatus.NONE -> TODO()
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