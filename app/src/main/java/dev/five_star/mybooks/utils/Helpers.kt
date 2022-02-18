package dev.five_star.mybooks.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DateFormat
import java.util.*



fun Int.divideToPercent(divideTo: Int): Double {
    return if (divideTo == 0) 0.0
    else (this / divideTo.toDouble()) * 100
}

fun roundOffDecimal(number: Double): Double {
    return BigDecimal(number).setScale(2, RoundingMode.HALF_EVEN).toDouble()
}

fun Date.uiFormat() : String {
    return  DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(this)
}