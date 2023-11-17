package taras.du.bluetooth.service

import android.bluetooth.BluetoothDevice
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothDeviceConnection {
    fun connectionStatus(): StateFlow<BluetoothStatus>
    fun pairedDevices(): List<BluetoothDevice>
    fun connect(device: BluetoothDevice): StateFlow<BluetoothStatus>
    fun disconnect(): StateFlow<BluetoothStatus>
}