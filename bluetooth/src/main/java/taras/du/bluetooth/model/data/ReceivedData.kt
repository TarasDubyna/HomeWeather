package taras.du.bluetooth.model.data

import taras.du.domain.model.device.DeviceParameter

@Deprecated(message = "Not used", level = DeprecationLevel.HIDDEN)
data class ReceivedData(
    val header: HeaderType,
    val paramValues: List<DeviceParameter>
) {
    /*override fun toString(): String = StringBuilder(header.shorName)
        .append(":")
        .append(paramValues.joinToString(separator = ",") { "${it.first.shortName}=${it.second.value}" })
        .toString()*/
}
