package taras.du.domain.model.device

enum class ParameterType(val shortName: String){
    TIMESTAMP("ts"),
    DEVICE_TIME("time"),
    SD_TOTAL_SPACE("sd_total"),
    SD_FREE_SPACE("sd_free"),
    SD_CLEAR("sd_clear"),
    RESTART("restart"),
    MEASUREMENT_FREQUENCY("freq"),
    SYNC_TICKS("s_ticks"),
    REAL_TICKS("r_ticks"),

    TEMPERATURE("temp"),
    HUMIDITY("hum"),
    ATMOSPHERE_PRESSURE("ap"),
    LIGHT_INTENSITY("li"),
    ALCOHOL("alc"),
    CO2("co");

    companion object {
        fun getByShortName(shortName: String): ParameterType? =
            enumValues<ParameterType>().firstOrNull { it.shortName == shortName }
    }

}
