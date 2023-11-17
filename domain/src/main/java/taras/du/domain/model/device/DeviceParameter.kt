package taras.du.domain.model.device

data class DeviceParameter constructor(val parameter: Parameter, val value: Value) {

    constructor(parameterCode: String, value: String) : this(
        parameter = Parameter.getByCode(parameterCode),
        value = Value.getValue(value)
    )

}