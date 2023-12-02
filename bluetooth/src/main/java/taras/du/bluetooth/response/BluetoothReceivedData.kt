package taras.du.bluetooth.response

interface BluetoothReceivedData {
    fun onDataReceived(buff: ByteArray)
}

/*interface BluetoothReceivedData: BluetoothService.OnBluetoothEventCallback {

    override fun onStatusChange(status: BluetoothStatus?) {}

    override fun onDeviceName(deviceName: String?) {}

    override fun onToast(message: String?) {}

    override fun onDataWrite(buffer: ByteArray?) {}

}*/
