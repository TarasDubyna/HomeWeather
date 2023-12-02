package taras.du.bluetooth.model

import taras.du.bluetooth.DeviceDataConverter


// params:param_1=value,param_2=value,param_3=value
// ticks: [ts=value,temp=value,hum=value],[ts=value,temp=value,hum=value,...],[ts=value,temp=value,hum=value,...]

sealed class ReceivedMessageModel(val type: Type) {

    data class DeviceParameters(val parameters: Map<String, String>): ReceivedMessageModel(Type.PARAMETER) {
        override fun toString(): String {
            return "DeviceParameters: ${parameters.toList().joinToString { "${it.first}=${it.second}" }}"
        }
    }

    data class Ticks(val ticks: List<Triple<Long, String, String>>): ReceivedMessageModel(Type.TICKS) {
       override fun toString(): String {
            return "Ticks: ${ticks.joinToString { "[ts=${it.first}, ${it.second}, ${it.third}]" }}"
        }
    }

    data class Unknown(val receivedMessage: String): ReceivedMessageModel(Type.UNKNOWN)

    companion object {
        fun create(receivedMessage: String): ReceivedMessageModel {
            return when(DeviceDataConverter.getReceivedMessageType(receivedMessage)) {
                MessageType.TICKS -> {
                    val ticks = DeviceDataConverter.getDeviceTicks(receivedMessage)
                    Ticks(ticks)
                }
                MessageType.DEVICE_PARAMETERS -> {
                    val params = DeviceDataConverter.getDeviceParameters(receivedMessage)
                    DeviceParameters(params)
                }
                else -> {
                    Unknown(receivedMessage)
                }
            }
        }
    }

    enum class Type(val shortName: String) {
        PARAMETER("params"), TICKS("ticks"), UNKNOWN("");
        companion object {
            fun getByShortName(shortName: String): Type =
                enumValues<Type>().firstOrNull { it.shortName == shortName } ?: UNKNOWN
        }
    }

}
