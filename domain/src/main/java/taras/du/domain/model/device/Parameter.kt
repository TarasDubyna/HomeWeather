package taras.du.domain.model.device

sealed class Parameter(val code: String) {
    sealed class SetParameter(code: String): Parameter(code) {
        data object Restart: SetParameter(ParameterCode.RESTART)
        data object ClearSdCard: SetParameter(ParameterCode.SD_CLEAR)
        data object SyncTicks: SetParameter(ParameterCode.SYNC_TICKS)
        data object RealTicks: SetParameter(ParameterCode.REAL_TICKS)
    }
    sealed class GetParameter(code: String): Parameter(code) {
        data object DeviceTime: GetParameter(ParameterCode.DEVICE_TIME)
        data object MeasurementFrequency: GetParameter(ParameterCode.MEASUREMENT_FREQUENCY)
        data object SdCardTotalSpace: GetParameter(ParameterCode.SD_TOTAL_SPACE)
        data object SdCardFreeSpace: GetParameter(ParameterCode.SD_FREE_SPACE)
    }
    sealed class TickParameter(code: String): Parameter(code) {
        data object Timestamp: TickParameter(ParameterCode.TIMESTAMP)
        data object Temperature: TickParameter(ParameterCode.TEMPERATURE)
        data object Humidity: TickParameter(ParameterCode.HUMIDITY)
        data object AtmospherePressure: TickParameter(ParameterCode.ATMOSPHERE_PRESSURE)
        data object LightIntensity: TickParameter(ParameterCode.LIGHT_INTENSITY)
        data object Alcohol: TickParameter(ParameterCode.ALCOHOL)
        data object Co2: TickParameter(ParameterCode.CO2)
    }
}

object ParameterCode {
    val TIMESTAMP = "ts"
    val DEVICE_TIME = "time"
    val SD_TOTAL_SPACE = "sd_total"
    val SD_FREE_SPACE = "sd_free"
    val SD_CLEAR = "sd_clear"
    val RESTART = "restart"
    val MEASUREMENT_FREQUENCY = "freq"
    val SYNC_TICKS = "s_ticks"
    val REAL_TICKS = "r_ticks"

    val TEMPERATURE = "temp"
    val HUMIDITY = "hum"
    val ATMOSPHERE_PRESSURE = "ap"
    val LIGHT_INTENSITY = "li"
    val ALCOHOL = "alc"
    val CO2 = "co"

}

/*enum class Parameter(val code: String) {

    TEMPERATURE("temp"),
    HUMIDITY("hum"),
    ATMOSPHERE_PRESSURE("ap"),
    LIGHT_INTENSITY("li"),
    ALCOHOL("alc"),
    CO2("co"),
    TIME("ts"),
    STORAGE_TOTAL("sd_t"),
    STORAGE_FREE("sd_f"),
    FREQUENCY("freq"),
    UNKNOWN("");

    companion object {
        fun getByCode(code: String): Parameter {
            return enumValues<Parameter>().firstOrNull { it.code == code} ?: UNKNOWN
        }
    }

}*/

enum class SetParameter(val code: String) {
    TIME("ts"),
    FREQUENCY("freq"),
    REAL_TICKS("r_ticks"),
    SYNC_TICKS("s_ticks"),
    RESTART("restart"),
    SD_CLEAR("sd_clear");
}

enum class GetParameter(val code: String) {
    TIME("ts"),
    FREQUENCY("freq"),
    REAL_TICKS("r_ticks"),
    SYNC_TICKS("s_ticks"),
    RESTART("restart"),
    SD_CLEAR("sd_clear");
}
