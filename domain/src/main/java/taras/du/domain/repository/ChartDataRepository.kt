package taras.du.domain.repository

import taras.du.domain.model.MeasurementType
import taras.du.domain.model.MeasurementValue
import taras.du.domain.model.chart.TimePeriod

interface ChartDataRepository {
    suspend fun getMeasurementValues(measurementType: MeasurementType, timePeriod: TimePeriod): List<MeasurementValue>
    suspend fun getMeasurementBounds(measurementType: MeasurementType, timePeriod: TimePeriod): Pair<Float,Float>
    suspend fun getMinMaxMeasurementValues(measurementType: MeasurementType, timePeriod: TimePeriod): Pair<MeasurementValue, MeasurementValue>
}