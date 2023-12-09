package taras.du.bluetooth.model

import taras.du.bluetooth.DeviceDataMapper

const val TICK_PARAM_SIZE = 7

class DeviceMessage(val type: String, val body: String) {

    var deviceSettings: DeviceSettings? = null
    var deviceTicks: List<DeviceTick> = emptyList()

    constructor(data: Map<String, String>) : this(
        type = "set",
        body = data.entries.joinToString { "${it.key}=${it.value}" }) {
        this.deviceSettings = convertToDeviceSettings(data)
    }

    constructor(data: Set<String>) : this("get", data.joinToString())

    constructor(receivedMessage: String) : this(
        receivedMessage.substringBefore(":"),
        receivedMessage.substringAfter(":")
    ) {
        when (type) {
            "params" -> {
                deviceSettings = getDeviceSettings(body)
            }
            "ticks" -> {
                deviceTicks = getDeviceTicks(body)
            }
        }
    }


    // <ticks: [ts=value,temp=value,hum=value,ap=value,light=value,alc=value,co2=value],[ts=value,temp=value,hum=value,ap=value,light=value,alc=value,co2=value]>
    private fun getDeviceTicks(messageBody: String): List<DeviceTick> {
        val ticksBody = messageBody.substringAfter(':')
        return DeviceDataMapper.splitTicksBody(ticksBody)
            .map { DeviceDataMapper.mapDeviceTicks(it) }
            .flatten()
    }

    private fun getDeviceSettings(messageBody: String): DeviceSettings {
        val paramValueMap = DeviceDataMapper.mapBodyToParamValueMap(messageBody.substringAfter(':'))
        return DeviceSettings(paramValueMap)
    }


    private fun convertToDeviceSettings(data: Map<String, String>): DeviceSettings {
        return DeviceSettings(
            time = data.getOrDefault("time", null)?.toLongOrNull(),
            measurementFrequency = data.getOrDefault("freq", null)?.toIntOrNull(),
            storageTotalSpace = data.getOrDefault("sd_total", null)?.toIntOrNull(),
            storageFreeSpace = data.getOrDefault("sd_total", null)?.toIntOrNull()
        )
    }


}
