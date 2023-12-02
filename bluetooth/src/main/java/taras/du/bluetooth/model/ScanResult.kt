package taras.du.bluetooth.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice

sealed class ScanResult {

    data object Started: ScanResult()

    data class FoundDevices(val devices: Set<BluetoothDevice>): ScanResult() {
        @SuppressLint("MissingPermission")
        override fun toString(): String {
            return devices.joinToString { "${it.name} [${it.address}]" }
        }
    }

    data object Stopped: ScanResult()

}
