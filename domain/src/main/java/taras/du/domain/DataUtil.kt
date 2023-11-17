package taras.du.domain

object DataUtil {

    fun convertBooleanToString(boolean: Boolean): String = if (boolean) "1" else "0"

    fun convertStringValueToBoolean(code: String): Boolean? = when(code) {
        "0" -> false
        "1" -> true
        else -> null
    }

}

