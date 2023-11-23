package taras.du.bluetooth.model.data

import taras.du.data.model.DataType
import taras.du.domain.model.device.DeviceParameter

sealed class DeviceResponseModel {

    data class DeviceParameters(val parameters: List<DeviceParameter>): DeviceResponseModel()
    data class Ticks(val ticks: List<TickDataModel>): DeviceResponseModel()

}
