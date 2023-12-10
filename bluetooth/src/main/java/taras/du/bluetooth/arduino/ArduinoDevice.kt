package taras.du.bluetooth.arduino

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import taras.du.bluetooth.model.DeviceMessage

interface ArduinoDevice {
    fun connectionState(): StateFlow<BluetoothStatus>
    suspend fun sendMessage(message: DeviceMessage)
    fun receivedMessages(): SharedFlow<DeviceMessage>
}