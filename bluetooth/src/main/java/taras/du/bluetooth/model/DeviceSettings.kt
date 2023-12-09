package taras.du.bluetooth.model

data class DeviceSettings constructor(
    var time: Long? = null,
    var measurementFrequency: Int? = null,
    var storageTotalSpace: Int? = null,
    var storageFreeSpace: Int? = null
) {

    constructor(paramValueMap: Map<String, String>): this() {
        time = paramValueMap["time"]?.toLongOrNull()
        measurementFrequency = paramValueMap["freq"]?.toIntOrNull()
        storageTotalSpace = paramValueMap["sd_total"]?.toIntOrNull()
        storageFreeSpace = paramValueMap["sd_free"]?.toIntOrNull()
    }

    // setting_1=value,setting_2=value
    override fun toString(): String {
        val settings = mutableMapOf(
            "time" to time.toString(),
            "freq" to measurementFrequency.toString(),
            "sd_total" to storageTotalSpace.toString(),
            "sd_free" to storageFreeSpace.toString()
        )
        return settings.entries.joinToString { "${it.key}=${it.value}" }
    }

}
