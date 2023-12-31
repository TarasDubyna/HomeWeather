package taras.du.bluetooth.model.data

import taras.du.domain.model.device.Parameter


class DeviceDataModel constructor(val parameters: Map<Parameter, String>) {

    constructor(parameters: Set<Parameter>) : this(parameters.associateWith { "" })

    constructor(data: String): this(convertStringDataToMap(data))


    companion object {
        private fun convertStringDataToMap(data: String): Map<Parameter, String> {
            val content = data.substringAfterLast(":").trim(':')
            return content.split(",").associate { parameterValue ->
                val parameterCode = parameterValue.substringBefore("=").trim('=')
                val value = parameterValue.substringAfter("=").trim('=')
                Pair(Parameter.getByCode(parameterCode), value)
            }
        }
    }



    /*fun parametersToString(): String = params.toList().joinToString() {
        val builder = StringBuilder(it.first.code)
        it.second?.let { value ->
            builder.append("=").append(value.toString())
        }
        builder.toString()
    }

    override fun toString(): String = StringBuilder(type.shorName)
        .append(":")
        .append(params.toList().joinToString(separator = ",") {
            "${it.first}=${it.second}"
        }
        )
        .toString()

    companion object {

        fun convert(buffer: ByteArray?): DeviceDataModel? {
            return buffer?.let { buf ->
                val message = String(buf)
                val sHeader = message.substringBefore(":").trim(':')
                val sContent = message.substringAfterLast(":").trim(':')

                val headerType = HeaderType.getByShortName(sHeader) ?: return null

                val paramValueMap = mutableMapOf<taras.du.domain.model.Param, ParamValue>()
                sContent.split(",", ignoreCase = true).forEach {
                    val value = ParamValue(it.substringAfterLast("=").trim('='))
                    taras.du.domain.model.Param.getByShortName(it.substringBefore("=").trim('='))
                        ?.let { param ->
                            paramValueMap[param] = value
                        }

                }
                DeviceDataModel(headerType, paramValueMap)
            }


        }

    }

    enum class HeaderType(val shorName: String) {

        GET("get"),
        SET("set"),
        TICKS("ticks");

        override fun toString(): String {
            return this.name.lowercase()
        }

        companion object {
            fun getByShortName(shorName: String): HeaderType? =
                enumValues<HeaderType>().firstOrNull { it.shorName == shorName }

        }

    }*/

}
