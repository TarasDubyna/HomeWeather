package taras.du.domain.repository

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import taras.du.domain.model.device.ScanningState
import kotlin.time.Duration

interface DeviceScannerRepository {
    fun scanningState(): StateFlow<ScanningState>
    fun startScanning(timeout: Duration): SharedFlow<BluetoothDevice>
    fun stopScanning()
}