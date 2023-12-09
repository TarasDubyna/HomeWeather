package taras.du.data.repositories

import kotlinx.coroutines.flow.Flow
import taras.du.domain.repository.DeviceScannerRepository
import javax.inject.Singleton

@Singleton
class DeviceScannerRepositoryImpl: DeviceScannerRepository {

    private val TAG = "DeviceScannerRepository"


    override fun startScanning(): Flow<ScanResult> {

    }

    override fun stopScanning() {
        TODO("Not yet implemented")
    }

}