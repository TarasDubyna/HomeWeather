package taras.du.domain.model.device

import java.util.Date
import kotlin.time.Duration

data class DeviceSettings(
    val deviceTime: Date,
    val measurementFrequency: Duration,
    val sdCardFreeSpace: Long,
    val sdCardTotalSpace: Long
)
