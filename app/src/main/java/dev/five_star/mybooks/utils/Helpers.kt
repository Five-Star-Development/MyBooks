package dev.five_star.mybooks

import java.math.RoundingMode
import java.text.DecimalFormat

fun Int.divideToPercent(divideTo: Int): Float {
    return if (divideTo == 0) 0f
    else (this / divideTo.toFloat()) * 100
}

fun roundOffDecimal(number: Float): Float {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.HALF_UP
    return df.format(number).toFloat()
}