package taras.du.bluetooth.model.data


data class DeviceData(
    val type: HeaderType,
    val params: Map<String, String>
) {

    override fun toString(): String = StringBuilder(type.shorName)
        .append(":")
        .append(params.toList().joinToString(separator = ",") { "${it.first}=${it.second}" })
        .toString()

    companion object {

        /*fun convert(buffer: ByteArray?): DeviceData? {
            return buffer?.let { buf ->
                val message = String(buf)
                val sHeader = message.substringBefore(":").trim(':')
                val sContent = message.substringAfterLast(":").trim(':')

                val headerType = HeaderType.getByShortName(sHeader) ?: return null

                val paramValueMap = mutableMapOf<taras.du.domain.model.Param, ParamValue>()
                sContent.split(",", ignoreCase = true).forEach {
                    val value = ParamValue(it.substringAfterLast("=").trim('='))
                    taras.du.domain.model.Param.getByShortName(it.substringBefore("=").trim('='))?.let { param ->
                        paramValueMap[param] = value
                    }

                }
                DeviceData(headerType, paramValueMap)
            }


        }*/

    }

}
