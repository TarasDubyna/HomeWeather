package taras.du.bluetooth

import taras.du.bluetooth.model.DeviceSettings
import taras.du.bluetooth.model.DeviceTick

object DeviceDataMapper {

    /**
     * @param ticksBody <[...],[...],[...]>.
     * @return List of array <ts,temp,hum,ap,li,alc,co2>.
     */
    fun splitTicksBody(ticksBody: String): List<Array<String>> {
        return ticksBody.removePrefix("[")
            .removeSuffix("]")
            .split("],[")
            .map { it.split(",").toTypedArray() }
    }

    /**
     * @param tickArray <ts,temp,hum,ap,li,alc,co2>.
     * @return List of device ticks of the same timestamp.
     */
    fun mapDeviceTicks(ticksArray: Array<String>): List<DeviceTick> {
        val ts = ticksArray.firstOrNull()?.toLongOrNull() ?: return emptyList()
        val ticks = mutableListOf<DeviceTick>()
        enumValues<DeviceTick.Type>().forEachIndexed { index, type ->
            val value = ticksArray.getOrNull(index) ?: return emptyList()
            ticks.add(DeviceTick(ts = ts, type = type.shortName, value = value))
        }
        return ticks
    }

    fun mapBodyToParamValueMap(messageBody: String): Map<String, String> = messageBody.split(",").associate {
            Pair(it.substringBefore("="), it.substringAfter("="))
        }


    fun mapParamValueMapToDeviceSettings(paramValueMap: Map<String, String>): DeviceSettings =
        DeviceSettings().apply {
            time = paramValueMap["time"]?.toLongOrNull()
            measurementFrequency = paramValueMap["freq"]?.toIntOrNull()
            storageTotalSpace = paramValueMap["sd_total"]?.toIntOrNull()
            storageFreeSpace = paramValueMap["sd_total"]?.toIntOrNull()
        }



}