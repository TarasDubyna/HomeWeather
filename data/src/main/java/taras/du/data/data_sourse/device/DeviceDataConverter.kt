package taras.du.data.data_sourse.device

import taras.du.data.model.DeviceTicksModel

object DeviceDataConverter {

    fun convertResponseToDeviceParams(response: ByteArray): Map<String, String> {
        val responseMessage = String(response)
        return responseMessage.substringAfterLast(":").split(",").associate {
            val param = it.substringBefore("=")
            val value = it.substringAfter("=")
            Pair(param, value)
        }
    }

    fun convertResponseToDeviceParams(responseMessage: String): Map<String, String> {
        return responseMessage.substringAfterLast(":").split(",").associate {
            val param = it.substringBefore("=")
            val value = it.substringAfter("=")
            Pair(param, value)
        }
    }


    // <ticks: [ts_value,temp_value,...],[]>
    fun convertResponseToDeviceTicks(responseMessage: String): List<DeviceTicksModel> {
        responseMessage.substringAfterLast(":")
    }

}