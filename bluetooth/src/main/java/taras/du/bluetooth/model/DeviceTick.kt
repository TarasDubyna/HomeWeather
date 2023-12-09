package taras.du.bluetooth.model

data class DeviceTick(val ts: Long, val type: String, val value: String) {
    enum class Type(val arrayIndex: Int, val shortName: String) {
        //TS(0, "ts"),
        TEMP(1, "temp"),
        HUM(2,"hum"),
        AP(3, "ap"),
        LI(4, "li"),
        ALC(5, "alc"),
        CO2(6, "co2");
    }
}
