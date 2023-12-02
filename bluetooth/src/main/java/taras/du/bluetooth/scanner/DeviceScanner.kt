package taras.du.bluetooth.scanner

import android.bluetooth.BluetoothDevice
import android.util.Log
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import taras.du.bluetooth.model.ScanResult
import taras.du.bluetooth.model.ScanningState
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

interface DeviceScanner {
    fun startScanning(): SharedFlow<ScanResult>
    fun stopScanning()
}

@Singleton
class DeviceScannerImpl @Inject constructor(private val service: BluetoothService): DeviceScanner {

    private val TAG = "DeviceScanner"

    private val emitPeriod = 2.seconds
    private val foundDevicesEmitter: Job = CoroutineScope(Dispatchers.Default).launch {
        while (true) {
            Log.d(TAG, "EMIT DEVICES: ${foundDevices.size}")

            val scanResult = ScanResult.FoundDevices(foundDevices)
            _scanResult.tryEmit(scanResult)
            foundDevices.clear()

            delay(emitPeriod)
        }
    }

    private val foundDevices = mutableSetOf<BluetoothDevice>()
    private val _scanResult = MutableSharedFlow<ScanResult>()

    private val _scanningState = MutableStateFlow<ScanningState>(ScanningState.STOPPED)
    

    override fun startScanning(): SharedFlow<ScanResult> {
        Log.d(TAG, "startScanning: ${_scanningState.value.name}")
        if (_scanningState.value != ScanningState.IN_PROCESS) service.startScan()

        service.setOnScanCallback(object : BluetoothService.OnBluetoothScanCallback {
            override fun onDeviceDiscovered(device: BluetoothDevice?, rssi: Int) {
                _scanningState.tryEmit(ScanningState.IN_PROCESS)
                device?.let {
                    foundDevices.add(it)
                }
            }

            override fun onStartScan() {
                Log.d(TAG, "START SCANNING: ")
                _scanningState.tryEmit(ScanningState.STARTED)
                _scanResult.tryEmit(ScanResult.Started)
                foundDevicesEmitter.start()
            }

            override fun onStopScan() {
                Log.d(TAG, "STOP SCANNING: ")
                _scanningState.tryEmit(ScanningState.STOPPED)
                _scanResult.tryEmit(ScanResult.Stopped)
                foundDevicesEmitter.cancel()
            }

        })
        return _scanResult.asSharedFlow()
    }

    override fun stopScanning() {
        Log.d(TAG, "stopScanning: ${_scanningState.value.name}")
        service.stopScan()
    }

}