package taras.du.bluetooth.service

import kotlinx.coroutines.flow.Flow

interface BluetoothDeviceScanner {
    fun startScanning(): Flow<ScanResult>
    fun stopScanning()
}