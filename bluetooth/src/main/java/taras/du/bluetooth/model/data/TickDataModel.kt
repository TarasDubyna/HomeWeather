package taras.du.bluetooth.model.data

import taras.du.domain.model.device.ParameterType

data class TickDataModel(val ts: Long, val type: ParameterType, val value: String)
