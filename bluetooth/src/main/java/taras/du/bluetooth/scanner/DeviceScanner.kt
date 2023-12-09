package taras.du.bluetooth.scanner

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import taras.du.bluetooth.model.ScanningState
import kotlin.time.Duration

interface DeviceScanner {
    val scannerState: StateFlow<ScanningState>
    fun startScanning(timeout: Duration): SharedFlow<BluetoothDevice>
    fun stopScanning()
}
