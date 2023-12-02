package taras.du.domain.model.device.sensor

import taras.du.domain.model.MeasurementType

class BME280Sensor {

    val measurements = arrayListOf(
        MeasurementType.TEMPERATURE,
        MeasurementType.HUMIDITY,
        MeasurementType.ATMOSPHERE_PRESSURE
    )



}