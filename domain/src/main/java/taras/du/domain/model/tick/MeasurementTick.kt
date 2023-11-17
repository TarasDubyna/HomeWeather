package taras.du.domain.model.tick

import taras.du.domain.model.MeasurementType
import java.util.Date

data class MeasurementTick(val type: MeasurementType, override val time: Date, override val value: Float):
    Tick
