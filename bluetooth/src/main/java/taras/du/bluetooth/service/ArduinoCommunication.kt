package taras.du.bluetooth.service

import kotlinx.coroutines.flow.Flow
import taras.du.bluetooth.model.RequestMessageModel

interface ArduinoCommunication {
    suspend fun sendData(request: RequestMessageModel): Flow<SendingDataResult>
}