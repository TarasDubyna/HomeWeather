package taras.du.bluetooth.model

import android.bluetooth.BluetoothDevice

sealed class ScanDevicesResult {
    data class Successful(val devices: Set<BluetoothDevice>): ScanDevicesResult()
    data class Failed(val exception: BluetoothException): ScanDevicesResult()
}