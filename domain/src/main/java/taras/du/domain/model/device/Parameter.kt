package taras.du.domain.model.device


enum class Parameter(val code: String) {

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

}
