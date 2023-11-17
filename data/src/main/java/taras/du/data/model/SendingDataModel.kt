package taras.du.data.model

class SendingDataModel(val type: Type) {

    enum class Type(val code: String) {

        GET("get"),
        SET("set");

        companion object {
            fun getByCode(code: String): Type? =
                enumValues<Type>().firstOrNull { it.code == code }
        }

    }


}