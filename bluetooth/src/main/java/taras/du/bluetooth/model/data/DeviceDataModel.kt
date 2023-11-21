package taras.du.bluetooth.model.data

import taras.du.data.model.DataType
import taras.du.domain.model.device.Parameter

const val GET_HEADER_NAME = "get"
const val SET_HEADER_NAME = "set"

class DeviceDataModel constructor(
    val dataType: DataType,
    val parameters: Map<Parameter, String>
) {


    constructor(parameters: Map<Parameter, String>) : this(DataType.SET, parameters)
    constructor(parameters: Set<Parameter>) : this(DataType.GET, parameters.associateWith { "" })
    constructor(receivedMessage: String) : this(parseDataType(receivedMessage), parseParameters(receivedMessage))

    fun getRequestMessage(): String {
        val builder = StringBuilder(dataType.type).append(":")
        val parametersString = parameters.toList().joinToString {
            val paramValueBuilder = StringBuilder(it.first.shortName)
            it.second.isNotEmpty().let { value ->
                paramValueBuilder.append("=").append(value)
            }
            paramValueBuilder.toString()
        }
        builder.append(parametersString)
        return builder.toString()
    }

    companion object {

        fun parseReceivedMessage(message: String): DeviceDataModel {
            val dataType = parseDataType(message)
            val content = message.substringAfter(":").trim(':')
            val parameters = parseParameters(content)
            return DeviceDataModel(dataType, parameters)
        }

        private fun parseDataType(receivedMessage: String): DataType {
            val sDataType = receivedMessage.substringBefore(":").trim(':')
            return enumValues<DataType>().firstOrNull { it.type == sDataType } ?: DataType.UNKNOWN
        }


        private fun parseParameters(receivedMessage: String): Map<Parameter, String> {
            val parameters = mutableMapOf<Parameter, String>()
            receivedMessage.substringAfter(":").trim(':').split(",")
                .forEach { sParamValue ->
                    val split = sParamValue.split("=")
                    split.firstOrNull()?.let {  sParam ->
                        Parameter.getByShortName(sParam)?.let { param ->
                            val value = split.lastOrNull() ?: ""
                            parameters[param] = value
                        }
                    }
                }
            return parameters
        }

/*
        private fun parseParameters(content: String): Map<Parameter, String> {
            val parameters = mutableMapOf<Parameter, String>()
            content.split(",")
                .forEach { sParamValue ->
                    val split = sParamValue.split("=")
                    split.firstOrNull()?.let {  sParam ->
                        Parameter.getByShortName(sParam)?.let { param ->
                            val value = split.lastOrNull() ?: ""
                            parameters[param] = value
                        }
                    }
                }
            return parameters
        }*/


    }

}
