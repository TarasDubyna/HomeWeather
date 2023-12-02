package taras.du.data.data_sourse.device

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import taras.du.bluetooth.model.GetRequest
import taras.du.bluetooth.model.ReceivedMessageModel
import taras.du.bluetooth.model.RequestMessageModel
import taras.du.bluetooth.model.SendingDataResult
import taras.du.bluetooth.model.SetRequest
import taras.du.bluetooth.model.data.DeviceDataModel
import taras.du.bluetooth.service.ArduinoCommunication
import taras.du.data.data_sourse.database.TickEntity
import taras.du.domain.model.device.ArduinoSettings
import taras.du.domain.model.device.ParameterType
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration

class ArduinoDataSource @Inject constructor(
    private val dataSender: ArduinoCommunication
): ArduinoAPI {


    override suspend fun getDeviceSettings(): Flow<ArduinoSettings> = callbackFlow {
        val parameters = mutableSetOf(
            ParameterType.DEVICE_TIME,
            ParameterType.SD_TOTAL_SPACE,
            ParameterType.SD_FREE_SPACE,
            ParameterType.MEASUREMENT_FREQUENCY
        ).map { it.shortName }.toSet()

        val request = GetRequest(parameters)
        CoroutineScope(Dispatchers.IO).launch {
            dataSender.sendData(request).collect { result ->
                if (result is SendingDataResult.Successful) {
                    result.receivedMessageModel.
                }
            }
        }
    }

    override suspend fun setDeviceTime(time: Date) {
        TODO("Not yet implemented")
    }

    override suspend fun setMeasurementFrequency(frequency: Duration) {
        TODO("Not yet implemented")
    }

    override suspend fun restartDevice(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun clearSDCard(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun startStreamTicks(): SharedFlow<TickEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun stopStreamTicks() {
        TODO("Not yet implemented")
    }

    override suspend fun startSyncTicks(): SharedFlow<TickEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun stopSyncTicks() {
        TODO("Not yet implemented")
    }


}