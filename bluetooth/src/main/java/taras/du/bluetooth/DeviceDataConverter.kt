package taras.du.bluetooth

import taras.du.bluetooth.model.MessageType

object DeviceDataConverter {

    const val HEADER_SET = "set"
    const val HEADER_GET = "get"
    const val HEADER_PARAMS = "params"
    const val HEADER_TICKS = "ticks"

    fun getReceivedMessageType(message: String): MessageType {
        val shortName = message.substringBefore(":")
        return MessageType.getByShortName(shortName)
    }

    fun getDeviceParameters(message: String): Map<String, String> {
        return message.substringAfter(":").split(",").associate {
            val sParam = it.substringBefore("=")
            val sValue = it.substringAfter("=")
            Pair(sParam, sValue)
        }
    }

    // ticks: [ts=value,temp=value,hum=value],[ts=value,temp=value,hum=value,...],[ts=value,temp=value,hum=value,...]
    fun getDeviceTicks(message: String): List<Triple<Long, String, String>> {
        if (message.substringBefore(":") != "ticks") return emptyList()

        val data = message.substringBefore(":")
            .split("],[")
            .map { it.trim('[').trim(']') }
            .map {
                it.split(",").associate { sParamValue ->
                    val sParam = sParamValue.substringBefore("=")
                    val sValue = sParamValue.substringAfter("=")
                    Pair(sParam, sValue)
                }
            }

        val ticks = mutableListOf<Triple<Long, String, String>>()
        data.forEach { map ->
            val ts = map.toMutableMap().remove("ts")?.toLongOrNull() ?: -1
            map.filter { it.key != "ts" }.forEach {
                ticks.add(Triple(ts, it.key, it.value))
            }
        }
        return ticks
    }


    fun toRequestMessage(data: Set<String>): String =
        StringBuilder("get")
            .append(":")
            .append(data.joinToString())
            .toString()

    fun toRequestMessage(data: Map<String, String>): String =
        StringBuilder("set")
            .append(":")
            .append(data.toList().joinToString { it.first + "=" + it.second })
            .toString()



}