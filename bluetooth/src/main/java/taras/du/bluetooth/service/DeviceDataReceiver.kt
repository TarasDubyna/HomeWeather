package taras.du.bluetooth.service

import android.util.Log
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import taras.du.bluetooth.model.data.DeviceDataModel

interface BluetoothEventObserver {
    fun receivedData(): SharedFlow<DeviceDataModel>
    fun connectionStatus(): StateFlow<BluetoothStatus>
}

class BluetoothEventObserverImpl(service: BluetoothService): BluetoothEventObserver {

    private val TAG = "BluetoothEventObserver"

    private val _receivedData: MutableSharedFlow<DeviceDataModel> = MutableSharedFlow()
    private val _connectionStatus: MutableStateFlow<BluetoothStatus> = MutableStateFlow(BluetoothStatus.NONE)

    private val eventCallback = object : BluetoothService.OnBluetoothEventCallback {
        override fun onDataRead(buffer: ByteArray?, length: Int) {
            buffer?.let {
                val receivedData = DeviceDataModel.convert(buffer)
                receivedData?.let {
                    logReceivedData()
                    _receivedData.tryEmit(it)
                }

            }
        }

        override fun onStatusChange(status: BluetoothStatus?) {
            status?.let {
                _connectionStatus.tryEmit(it)
            }
        }

        override fun onDeviceName(deviceName: String?) {
        }

        override fun onToast(message: String?) {
            message?.let { Log.e(TAG, "eventCallback.onToast: $it") }
        }

        override fun onDataWrite(buffer: ByteArray?) {

        }

    }

    init {
        service.setOnEventCallback(eventCallback)
    }

    override fun receivedData(): SharedFlow<DeviceDataModel> {
        return _receivedData
            .onEach { Log.d(TAG, "RECEIVED DATA: ${it.toString()}") }
            .shareIn(
                scope = CoroutineScope(Dispatchers.Default),
                started = SharingStarted.WhileSubscribed()
            )
    }

    override fun connectionStatus(): StateFlow<BluetoothStatus> {
        return _connectionStatus.asStateFlow()
    }

    private fun logReceivedData() {
        CoroutineScope(Dispatchers.Default).launch {
            _receivedData.shareIn(
                scope = CoroutineScope(Dispatchers.Default),
                started = SharingStarted.Eagerly
            ).collect {
                Log.d(TAG, "RECEIVED DATA: ${it.toString()}")
            }
        }
    }

}
