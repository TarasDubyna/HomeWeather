package taras.du.bluetooth.response

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import taras.du.bluetooth.model.ReceivedMessageModel
import taras.du.bluetooth.service.AppBluetoothService

class ResponseReceive(appBluetoothService: AppBluetoothService) {

    val receivedData: SharedFlow<ReceivedMessageModel> = MutableSharedFlow()

    fun onDataReceived(buff: ByteArray?) {
        buff?: return
        val message = String(buff)
        val data = ReceivedMessageModel(message)

    }

}