package taras.du.bluetooth.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import taras.du.bluetooth.model.SendingDataResult
import taras.du.bluetooth.model.ReceivedMessageModel
import taras.du.bluetooth.model.RequestMessageModel

interface ArduinoCommunication {
    suspend fun sendData(request: RequestMessageModel): Flow<SendingDataResult>
}