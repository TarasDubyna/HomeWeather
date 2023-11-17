package taras.du.bluetooth.parser
/*

import taras.du.bluetooth.model.data.DeviceData
import taras.du.bluetooth.model.data.HeaderType

object DataParser {
    fun parseReceivedData(buffer: ByteArray): DeviceData? {
        val message = String(buffer)
        val header = parseHeader(message) ?: return null
        val params = parseContent(message)

        return DeviceData(header, params)
    }

    private fun parseHeader(message: String): HeaderType? {
        return HeaderType.getByShortName(message.substringBefore(":").trim(':'))
    }

    private fun parseContent(message: String): Map<Param, ParamValue> {
        val map = mutableMapOf<Param, ParamValue>()
        message.substringBeforeLast(":").split(",").forEach {
            val param = Param.getByShortName(it.substringBefore("=").trim('='))
            val paramValue = ParamValue(it.substringAfterLast("=").trim('='))
            param?.let {
                map[param] = paramValue
            }
        }
        return map
    }

}
*/
