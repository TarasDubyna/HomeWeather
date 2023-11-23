package taras.du.bluetooth.service

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.util.Log
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothClassicService
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothConfiguration
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import taras.du.bluetooth.BluetoothUtil
import taras.du.bluetooth.model.BluetoothNotEnabledException
import taras.du.bluetooth.model.BluetoothDeviceNotConnectedException
import taras.du.bluetooth.model.BluetoothException
import taras.du.bluetooth.model.BluetoothOtherException
import taras.du.bluetooth.model.BluetoothPermissionNotGrantedException
import taras.du.bluetooth.model.SendingDataResult
import taras.du.bluetooth.model.data.DeviceDataModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

/*interface AppBluetoothService {
    
}*/

@Singleton
class AppBluetoothService @Inject constructor(
    @ApplicationContext private val appContext: Context
): BluetoothDeviceConnection, ArduinoCommunication {

    private val TAG = "BluetoothService"

    private val configuration = BluetoothConfiguration().apply {
        context = appContext
        bluetoothServiceClass = BluetoothClassicService::class.java
        bufferSize = 1024
        characterDelimiter = '\n'
        deviceName = "HW Mobile"
        callListenersInMainThread = false
        uuid = null
    }

    private val _receivedData = MutableSharedFlow<DeviceDataModel>()
    private val eventObserver: BluetoothEventObserver


    init {
        BluetoothService.init(configuration)
        eventObserver = BluetoothEventObserverImpl(BluetoothService.getDefaultInstance())
    }

    override fun connectionStatus(): StateFlow<BluetoothStatus> {
        return eventObserver.connectionStatus()
    }

    @SuppressLint("MissingPermission")
    override fun pairedDevices(): List<BluetoothDevice> {
        val manager: BluetoothManager = appContext.getSystemService(BluetoothManager::class.java)
        return manager.adapter.bondedDevices.toList()
    }

    override fun connect(device: BluetoothDevice): StateFlow<BluetoothStatus> {
        /*BluetoothService.getDefaultInstance()
            .setOnEventCallback(object : BluetoothService.OnBluetoothEventCallback {
                override fun onDataRead(buffer: ByteArray?, length: Int) {
                    buffer?.let {
                        Log.d(TAG, "onDataRead: ${it.toString()} size=$length")
                        val receivedData = parseReceivedData(it)
                        _receivedData.tryEmit(receivedData)
                    }
                }

                override fun onStatusChange(status: BluetoothStatus?) {
                    status?.let {
                        Log.d(TAG, "STATUS_CHANGE: ${it.name}")
                        trySend(it)
                    }
                }

                override fun onDeviceName(deviceName: String?) {
                    deviceName?.let {
                        Log.d(TAG, "DEVICE_NAME: $it")
                    }
                }

                override fun onToast(message: String?) {
                    message?.let {
                        Log.d(TAG, "TOAST: $it")
                    }
                }

                override fun onDataWrite(buffer: ByteArray?) {
                    buffer?.let {
                        Log.d(TAG, "DATA_WRITE: ${it.toString()}")
                    }
                }

            })*/
        BluetoothService.getDefaultInstance().connect(device)
        return eventObserver.connectionStatus()
    }

    override fun disconnect(): StateFlow<BluetoothStatus> {
        Log.d(TAG, "DISCONNECT: ")
        BluetoothService.getDefaultInstance().disconnect()
        return eventObserver.connectionStatus()
    }


    override suspend fun sendData(sendingData: DeviceDataModel) = callbackFlow<SendingDataResult> {
        try {
            checkBluetoothExceptions()

            val message = sendingData.getRequestMessage()
            launch(Dispatchers.IO) {
                BluetoothWriter(BluetoothService.getDefaultInstance()).writeln(message)
            }

            Log.d(TAG, "sendData: SUCCESSFUL: <$message>")

            val callback = object : BluetoothService.OnBluetoothEventCallback {
                override fun onDataRead(buffer: ByteArray?, length: Int) {
                    buffer?.let {buf ->
                        val receivedMessage = String(buf)
                        val receivedData = DeviceDataModel(receivedMessage)

                        val isResponseContainsParameters = receivedData.parameters.all { param ->
                            sendingData.parameters.map { it.type }.contains(param.type)
                        }

                        if (isResponseContainsParameters) {
                            Log.d(TAG, "sendData: RESPONSE: $receivedMessage")
                            trySend(SendingDataResult.Successful(receivedData))
                        }
                    }
                }

                override fun onStatusChange(status: BluetoothStatus?) {}

                override fun onDeviceName(deviceName: String?) {}

                override fun onToast(message: String?) {}

                override fun onDataWrite(buffer: ByteArray?) {}

            }
            BluetoothService.getDefaultInstance().setOnEventCallback(callback)

        } catch (e: BluetoothException) {
            Log.e(TAG, "sendData: FAILED: Bluetooth exception - ${e.message}")
            trySend(SendingDataResult.Failed(e))
        } catch (e: Exception) {
            Log.e(TAG, "sendData: FAILED: Another exception - ${e.message}")
            trySend(SendingDataResult.Failed(BluetoothOtherException(e.message?: e.toString())))
        }
    }

    override fun receivedData(): SharedFlow<DeviceDataModel> {
        return _receivedData.shareIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.WhileSubscribed()
        )
    }

    @Throws(BluetoothException::class)
    private fun checkBluetoothExceptions() {
        if (!BluetoothUtil.isBluetoothEnabled(appContext)) throw BluetoothNotEnabledException()
        if (!BluetoothUtil.isPermissionsGranted(appContext)) throw BluetoothPermissionNotGrantedException()
        if (BluetoothService.getDefaultInstance().status != BluetoothStatus.CONNECTED) throw BluetoothDeviceNotConnectedException()
    }



}
