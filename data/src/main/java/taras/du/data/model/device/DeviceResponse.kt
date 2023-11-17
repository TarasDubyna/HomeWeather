package taras.du.data.model.device

import taras.du.bluetooth.model.BluetoothException
import taras.du.domain.model.device.DeviceParameter
import taras.du.domain.model.tick.MeasurementTick


sealed class DeviceResponse {
    data class Parameters(val parameters: List<DeviceParameter>): DeviceResponse()
    data class Ticks(val ticks: List<MeasurementTick>): DeviceResponse()
    data class Error(val exception: BluetoothException): DeviceResponse()

}
