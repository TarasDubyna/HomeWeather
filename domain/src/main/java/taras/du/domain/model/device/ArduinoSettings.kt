package taras.du.domain.model.device

import java.util.Date
import kotlin.time.Duration

data class ArduinoSettings(
    val deviceTime: Date,
    val measurementFrequency: Duration,
    val sdCardFreeSpace: Long,
    val sdCardTotalSpace: Long;

    companion object {

    }

)
