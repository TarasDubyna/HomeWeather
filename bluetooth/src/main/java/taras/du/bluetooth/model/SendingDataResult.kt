package taras.du.bluetooth.model

sealed class SendingDataResult {
    data object Successful: SendingDataResult()
    data class Failed(val exceptions: BluetoothException): SendingDataResult()
}
