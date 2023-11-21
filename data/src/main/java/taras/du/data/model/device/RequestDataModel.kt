package taras.du.data.model.device

import taras.du.domain.model.device.Parameter

/*class RequestDataModel constructor(val parameters: Map<Parameter, String>) {

    constructor(parameters: Set<Parameter>) : this(parameters.associateWith { "" })

    override fun toString(): String {
        return parameters.toList().joinToString {
            val builder = StringBuilder(it.first.code)
            it.second.isNotEmpty().let { value -> builder.append("=").append(value) }
            builder.toString()
        }
    }
}*/



sealed class RequestDataModel {

    data class SetRequest(val data: Map<Parameter.SetParameter, String>) : RequestDataModel() {
        override fun toString(): String {
            val builder = StringBuilder(SET_HEADER_NAME)
            builder.append(
                data.toList()
                    .joinToString { "${it.first.code}=${it.second}" })
            return builder.toString()
        }
    }

    data class GetRequest(val data: Set<Parameter.GetParameter>) : RequestDataModel() {
        override fun toString(): String {
            val builder = StringBuilder(SET_HEADER_NAME)
            return builder.toString()
        }
    }

}

