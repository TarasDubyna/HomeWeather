package taras.du.domain.model.device

data class DeviceParameter(val type: ParameterType, val value: String) {
    override fun toString(): String {
        val builder = StringBuilder(type.shortName)
        if (value.isNotEmpty()) builder.append("=").append(value)
        return builder.toString()
    }
}
