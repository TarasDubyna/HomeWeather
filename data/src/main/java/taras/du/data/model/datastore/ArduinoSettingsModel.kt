package taras.du.data.model.datastore

import java.util.Date

data class ArduinoSettingsModel(
    val lastSyncTime: Date? = null,
    val storageTotalSpace: Int = -1,
    val storageFreeSpace: Int = -1,
    val tickFrequency: Int = -1
)
