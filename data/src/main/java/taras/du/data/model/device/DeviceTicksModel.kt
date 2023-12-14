package taras.du.data.model.device

data class DeviceTicksModel(
    val ts: String,
    val temperature: String,
    val humidity: String,
    val pressure: String,
    val lightIntensity: String,
    val alcohol: String,
    val co2: String
)
