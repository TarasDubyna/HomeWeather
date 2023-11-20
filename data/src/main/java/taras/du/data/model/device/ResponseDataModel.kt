package taras.du.data.model.device

import taras.du.bluetooth.model.BluetoothException
import taras.du.domain.model.device.Parameter
import taras.du.domain.model.tick.MeasurementTick


sealed class ResponseDataModel {
    data class GetResponse(val parameters: Map<Parameter.GetParameter, String>): ResponseDataModel()
    data class SetResponse(val ticks: Map<Parameter.SetParameter, String>): ResponseDataModel()
    data class TickResponse(val isSync: Boolean, val ticks: List<MeasurementTick>): ResponseDataModel()
    data class Error(val exception: BluetoothException): ResponseDataModel()

}
