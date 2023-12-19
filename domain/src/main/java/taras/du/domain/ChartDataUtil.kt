package taras.du.domain

import taras.du.domain.model.tick.Tick
import java.text.SimpleDateFormat
import java.util.Date

object ChartDataUtil {

    const val WEEK_PERIOD_DATE_FORMAT = "dd-MMM-yyyyy"

    fun computeWeekData(data: List<Tick>): Map<String, Float> {
        return data.map {
            val date = SimpleDateFormat(WEEK_PERIOD_DATE_FORMAT).format(Date(it.ts))
            Pair(date, it.value)
        }.groupBy { it.first }
            .mapValues {
                it.value.map {
                    value -> value.second
                }.average().toFloat()
            }
    }

}