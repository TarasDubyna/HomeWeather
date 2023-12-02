package taras.du.bluetooth.service

import kotlinx.coroutines.flow.Flow
import taras.du.bluetooth.model.ScanResult

interface BluetoothDeviceScanner {
    fun startScanning(): Flow<ScanResult>
    fun stopScanning()
}