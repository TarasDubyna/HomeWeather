package taras.du.bluetooth.model

enum class MessageType(val shortName: String) {

    GET("get"),
    SET("set"),
    TICKS("ticks"),
    DEVICE_PARAMETERS("params"),
    UNKNOWN("");

    companion object {
        fun getByShortName(shortName: String): MessageType =
            enumValues<MessageType>().firstOrNull { it.shortName == shortName } ?: UNKNOWN
    }

}
