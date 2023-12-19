package taras.du.domain.model.chart

import taras.du.domain.model.MeasurementType

data class ChartDataModel(
    val measurementType: MeasurementType,
    val data: List<Pair<String, Float>>,
    val bounds: Pair<Float, Float>
    )