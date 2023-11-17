package taras.du.domain.repository

import kotlinx.coroutines.flow.Flow
import taras.du.bluetooth.model.scanning.ScanResult

interface DeviceScannerRepository {
    fun startScanning(): Flow<taras.du.bluetooth.model.scanning.ScanResult>
    fun stopScanning()
}