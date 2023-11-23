package taras.du.bluetooth.parser

import taras.du.bluetooth.model.data.DeviceDataModel
import taras.du.bluetooth.model.data.DeviceResponseModel
import taras.du.data.model.DataType
import taras.du.domain.model.device.DeviceParameter
import taras.du.domain.model.device.ParameterType

object DataWrapper {

    fun getDataType(receivedMessage: String): DataType {
        val sDataType = receivedMessage.substringBefore(":").trim(':')
        return enumValues<DataType>().firstOrNull { it.type == sDataType } ?: DataType.UNKNOWN
    }

    fun getDeviceParameters(receivedMessage: String): List<DeviceParameter> {
        return receivedMessage.substringAfter(":")
            .split(",")
            .mapNotNull { getDeviceParameter(it) }
    }

    fun getDeviceParameter(sParamValue: String): DeviceParameter? {
        val type = ParameterType.getByShortName(sParamValue.substringBefore("=")) ?: return null
        val value = sParamValue.substringAfter("=")
        return DeviceParameter(type, value)
    }


    fun parseDeviceResponse(receivedMessage: String): DeviceResponseModel? {
        return when(getDataType(receivedMessage)){
            DataType.GET, DataType.SET -> {
                val parameters = getDeviceParameters(receivedMessage)
                DeviceResponseModel.DeviceParameters(parameters)
            }
            DataType.TICKS -> {
                val parameters = getDeviceParameters(receivedMessage)

                val ts = parameters.firstOrNull {
                    it.type == ParameterType.TIMESTAMP
                }?.let { it.value.toLongOrNull() ?: -1 } ?: run { -1 }

                DeviceResponseModel.Ticks(ts = ts)
            }
            DataType.UNKNOWN -> null
        }
    }




}
