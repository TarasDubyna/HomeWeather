package taras.du.domain.model.tick

import java.util.Date

//data class Tick(open val time: Date, open val value: Float)

interface Tick {
    val time: Date
    val value: Float
}

