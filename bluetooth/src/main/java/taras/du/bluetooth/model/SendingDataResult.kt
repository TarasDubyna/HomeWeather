package taras.du.bluetooth.model

sealed class SendingDataResult {
    data class Successful(val receivedMessageModel: ReceivedMessageModel): SendingDataResult()
    data class Failed(val exception: BluetoothException): SendingDataResult()
}
