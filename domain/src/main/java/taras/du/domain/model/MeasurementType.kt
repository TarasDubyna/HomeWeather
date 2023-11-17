package taras.du.domain.model

enum class MeasurementType(val shortName: String) {

    TEMPERATURE("temp"),
    HUMIDITY("hum"),
    ATMOSPHERE_PRESSURE("ap"),
    LIGHT_INTENSITY("li"),
    ALCOHOL("alc"),
    CO2("co"),
    UNKNOWN("");

    companion object {
        fun getByShortName(shortName: String): MeasurementType =
            enumValues<MeasurementType>().firstOrNull { it.shortName == shortName }?: UNKNOWN
    }

}