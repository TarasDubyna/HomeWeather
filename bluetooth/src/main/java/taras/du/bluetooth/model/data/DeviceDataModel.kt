package taras.du.bluetooth.model.data

import taras.du.bluetooth.parser.DataWrapper
import taras.du.data.model.DataType
import taras.du.domain.model.device.DeviceParameter
import taras.du.domain.model.device.ParameterType

const val GET_HEADER_NAME = "get"
const val SET_HEADER_NAME = "set"

class DeviceDataModel constructor(
    val dataType: DataType,
    val parameters: List<DeviceParameter>
) {


    constructor(parameters: List<DeviceParameter>) : this(DataType.SET, parameters)
    constructor(parameterTypes: Set<ParameterType>) : this(DataType.GET, parameterTypes.map { DeviceParameter(it, "") })
    constructor(receivedMessage: String) : this(DataWrapper.getDataType(receivedMessage), DataWrapper.getDeviceParameters(receivedMessage))

    fun getRequestMessage(): String {
        return StringBuilder(dataType.type)
            .append(":")
            .append(parameters.toList().joinToString { it.toString() })
            .toString()
    }

}
