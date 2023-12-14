package taras.du.data.model

import java.util.Date

data class DeviceSettingsModel(
    val lastSyncTime: Date? = null,
    val storageTotalSpace: Int = -1,
    val storageFreeSpace: Int = -1,
    val tickFrequency: Int = -1
)
