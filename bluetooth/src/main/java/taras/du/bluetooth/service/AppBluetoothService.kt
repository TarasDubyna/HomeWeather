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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import taras.du.bluetooth.BluetoothUtil
import taras.du.bluetooth.model.BluetoothNotEnabledException
import taras.du.bluetooth.model.BluetoothDeviceNotConnectedException
import taras.du.bluetooth.model.BluetoothException
import taras.du.bluetooth.model.BluetoothOtherException
import taras.du.bluetooth.model.BluetoothPermissionNotGrantedException
import taras.du.bluetooth.model.RequestMessageModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class AppBluetoothService @Inject constructor(
    @ApplicationContext private val appContext: Context
) : BluetoothDeviceConnection, ArduinoCommunication {

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

    private val _receivedData = MutableSharedFlow<ReceivedMessageModel>()
    private val eventObserver: BluetoothServiceEventObserver


    init {
        BluetoothService.init(configuration)
        eventObserver = BluetoothServiceEventObserverImpl(BluetoothService.getDefaultInstance())
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


    override suspend fun sendData(request: RequestMessageModel): Flow<SendingDataResult> =
        callbackFlow {
            try {
                checkBluetoothExceptions()

                eventObserver.deviceResponse().filter { response ->
                    when (response) {
                        is ReceivedMessageModel.DeviceParameters -> {
                            val filteredParameters = response.parameters.filterKeys {
                                request.getRequestParameters().keys.contains(it)
                            }
                            filteredParameters.keys.containsAll(request.getRequestParameters().keys)
                        }

                        is ReceivedMessageModel.Ticks -> {
                            request.getRequestParameters().entries
                                .firstOrNull { (it.key == "r_ticks" || it.key == "s_ticks") && it.value == "true" } != null
                        }

                        else -> {
                            false
                        }
                    }
                }.collect {
                    val sendingResult = SendingDataResult.Successful(it)
                    trySend(sendingResult)
                }

                val message = request.toString()
                launch(Dispatchers.IO) {
                    BluetoothWriter(BluetoothService.getDefaultInstance()).writeln(message)
                }
                Log.d(TAG, "sendData: SUCCESSFUL: <$message>")

            } catch (e: BluetoothException) {
                Log.e(TAG, "sendData: FAILED: Bluetooth exception - ${e.message}")
                trySend(SendingDataResult.Failed(e))
            } catch (e: Exception) {
                Log.e(TAG, "sendData: FAILED: Another exception - ${e.message}")
                trySend(
                    SendingDataResult.Failed(
                        BluetoothOtherException(
                            e.message ?: e.toString()
                        )
                    )
                )
            }
        }

    @Throws(BluetoothException::class)
    private fun checkBluetoothExceptions() {
        if (!BluetoothUtil.isBluetoothEnabled(appContext)) throw BluetoothNotEnabledException()
        if (!BluetoothUtil.isPermissionsGranted(appContext)) throw BluetoothPermissionNotGrantedException()
        if (BluetoothService.getDefaultInstance().status != BluetoothStatus.CONNECTED) throw BluetoothDeviceNotConnectedException()
    }


}
