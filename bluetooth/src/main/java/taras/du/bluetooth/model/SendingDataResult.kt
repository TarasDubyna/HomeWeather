package taras.du.bluetooth.model

import taras.du.bluetooth.model.data.DeviceDataModel

sealed class SendingDataResult {
    data class Successful(val deviceDataModel: DeviceDataModel): SendingDataResult()
    data class Failed(val exceptions: BluetoothException): SendingDataResult()
}
