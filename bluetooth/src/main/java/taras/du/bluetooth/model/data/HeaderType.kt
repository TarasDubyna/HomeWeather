package taras.du.bluetooth.model.data

enum class HeaderType(val shorName: String) {

    GET("get"),
    SET("set"),
    TICKS("ticks");

    override fun toString(): String {
        return this.name.lowercase()
    }

    companion object {
        fun getByShortName(shorName: String): HeaderType? =
            enumValues<HeaderType>().firstOrNull { it.shorName == shorName }

    }

}
