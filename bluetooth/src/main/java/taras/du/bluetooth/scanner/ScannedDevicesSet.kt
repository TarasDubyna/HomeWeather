package taras.du.bluetooth.scanner

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class ScannedDevicesFlow(val scope: CoroutineScope, val emitDelay: Duration) {

    private val _scannedDevices: MutableSharedFlow<BluetoothDevice> = MutableSharedFlow()
    val scannedDevices: SharedFlow<BluetoothDevice> = _scannedDevices


    fun addDevice

    fun emitDataJob(): Job {
        return scope.launch {
            while(isActive) {
                //todo:: emit data
                delay(emitDelay.toJavaDuration())
            }
        }
    }

    //start the loop
    val repeatFun = repeatRequest()

//Cancel the loop
    repeatFun.cancel()



}
