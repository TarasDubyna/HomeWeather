package taras.du.bluetooth.service

import kotlinx.coroutines.flow.Flow
import taras.du.bluetooth.model.scanning.ScanResult

interface BluetoothDeviceScanner {
    fun startScanning(): Flow<ScanResult>
    fun stopScanning()
}