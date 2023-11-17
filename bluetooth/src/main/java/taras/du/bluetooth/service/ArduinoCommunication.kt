package taras.du.bluetooth.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import taras.du.bluetooth.model.SendingDataResult
import taras.du.bluetooth.model.data.DeviceData

interface ArduinoCommunication {
    suspend fun sendData(data: DeviceData): Flow<SendingDataResult>
    fun receivedData(): SharedFlow<DeviceData>
}