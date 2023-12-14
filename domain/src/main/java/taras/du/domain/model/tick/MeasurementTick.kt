package taras.du.domain.model.tick

import taras.du.domain.model.MeasurementType
import java.util.Date

data class MeasurementTick(val type: MeasurementType, val time: Date, val value: Float)
