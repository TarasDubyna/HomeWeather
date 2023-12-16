package taras.du.data.data_sourse.device

import android.util.Log
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import taras.du.data.data_sourse.database.TickEntity
import taras.du.data.model.DeviceTicksModel
import taras.du.domain.model.device.DeviceState
import taras.du.domain.model.tick.Tick
import javax.inject.Singleton

@Singleton
class DeviceDataSourceImpl : DeviceDataSource {

    private val TAG = "DeviceDataSource"

    private val _deviceState: MutableStateFlow<DeviceState> = MutableStateFlow(DeviceState.NOT_CONNECTED)
    override val deviceState: StateFlow<DeviceState> = _deviceState

    private val responseFlow: MutableSharedFlow<String> = MutableSharedFlow()

    init {
        BluetoothService.getDefaultInstance().setOnEventCallback(object : BluetoothService.OnBluetoothEventCallback {
            override fun onDataRead(buffer: ByteArray?, length: Int) {
                if (buffer == null) return
                val responseMessage = String(buffer)
                Log.d(TAG, "RESPONSE MESSAGE: $responseMessage")
                responseFlow.tryEmit(responseMessage)
            }

            override fun onStatusChange(status: BluetoothStatus?) {
                status ?: return
                Log.d(TAG, "NEW BLUETOOTH STATUS: ${status.name}")
                when(status){
                    BluetoothStatus.CONNECTED -> _deviceState.tryEmit(DeviceState.CONNECTED)
                    BluetoothStatus.CONNECTING -> _deviceState.tryEmit(DeviceState.CONNECTING)
                    BluetoothStatus.NONE -> {

                    }
                }
            }

            override fun onDeviceName(deviceName: String?) {
            }

            override fun onToast(message: String?) {
            }

            override fun onDataWrite(buffer: ByteArray?) {
                buffer?.let {
                    Log.d(TAG, "SEND MESSAGE: ${String(it)}")
                }
            }

        })
    }

    override suspend fun getDeviceParams(params: Set<String>): Flow<Map<String, String>> =
        callbackFlow {
            responseFlow.filter { response ->
                (response.substringBefore(":") == "set") && (DeviceDataConverter.convertResponseToDeviceParams(
                    response
                ).keys.containsAll(params))
            }.collectLatest {response ->
                val paramValues = DeviceDataConverter.convertResponseToDeviceParams(response)
                Log.d(
                    TAG,
                    "RECEIVED MESSAGE: setDeviceParams: ${paramValues.entries.joinToString { "${it.key}=${it.value}" }}"
                )
                trySend(paramValues)
            }
            sendRequest(
                requestType = "get",
                body = params.joinToString()
            )
        }

    override suspend fun setDeviceParams(params: Map<String, String>): Flow<Map<String, String>> =
        callbackFlow {
            responseFlow.filter { response ->
                (response.substringBefore(":") == "set") && (DeviceDataConverter.convertResponseToDeviceParams(
                    response
                ).keys.containsAll(params.keys))
            }.collectLatest {response ->
                val paramValues = DeviceDataConverter.convertResponseToDeviceParams(response)
                Log.d(
                    TAG,
                    "RECEIVED MESSAGE: setDeviceParams: ${paramValues.entries.joinToString { "${it.key}=${it.value}" }}"
                )
                trySend(paramValues)
            }

            sendRequest(
                requestType = "set",
                body = params.entries.joinToString { "${it.key}=${it.value}" }
            )
        }

    override suspend fun getStoredTicks(): SharedFlow<List<DeviceTicksModel>> {
        val ticksFlow = MutableSharedFlow<TickEntity>()
    }


    private fun sendRequest(requestType: String, body: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val message = StringBuilder(requestType)
                .append(":")
                .append(body)
                .toString()
            val writer = BluetoothWriter(BluetoothService.getDefaultInstance())
            writer.writeln(message)
        }
    }


}

