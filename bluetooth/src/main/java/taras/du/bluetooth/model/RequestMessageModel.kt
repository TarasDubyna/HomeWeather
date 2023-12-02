package taras.du.bluetooth.model

import taras.du.bluetooth.DeviceDataConverter

abstract class RequestMessageModel {

    abstract fun getMessage(): String
    abstract fun getRequestParameters(): Map<String, String>

}


data class SetRequest(private val data: Map<String, String>) : RequestMessageModel() {
    override fun getMessage(): String {
        return DeviceDataConverter.toRequestMessage(data)
    }

    override fun getRequestParameters(): Map<String, String> {
        return data
    }
}


data class GetRequest(private val data: Set<String>) : RequestMessageModel() {
    override fun getMessage(): String {
        return DeviceDataConverter.toRequestMessage(data)
    }

    override fun getRequestParameters(): Map<String, String> {
        return data.associateWith { "" }
    }
}
