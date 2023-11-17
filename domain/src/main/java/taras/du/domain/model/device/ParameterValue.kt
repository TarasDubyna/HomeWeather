package taras.du.domain.model.device

import taras.du.domain.DataUtil.convertBooleanToString
import taras.du.domain.DataUtil.convertStringValueToBoolean

sealed class Value(stringValue: String) {

    data class StringValue(val value: String): Value(value)
    data class BooleanValue(val value: Boolean): Value(convertBooleanToString(value))
    data class LongValue(val value: Long): Value(value.toString())

    companion object {

        fun getValue(stringValue: String): Value {
            stringValue.toLongOrNull()?.let { return LongValue(it) }
            convertStringValueToBoolean(stringValue)?.let { return BooleanValue(it) }
            return StringValue(stringValue)
        }


    }

}

