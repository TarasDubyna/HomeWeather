package taras.du.data.model

data class DeviceTicksModel(
    val ts: String,
    val temp: String,
    val hum: String,
    val ap: String,
    val li: String,
    val alc: String,
    val co2: String
)
