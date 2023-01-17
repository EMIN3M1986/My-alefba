package com.example.myalefba.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object ExtensionFunction {
    fun Double.convertPrice(supportDecimal: Boolean = true, decimalCount: Int = 0): String {
        if (supportDecimal) {
            if (decimalCount > 0) {
                val numberSigns = generateNumberSigns(decimalCount)
                val decimalFormat = DecimalFormat(
                    "#,###.$numberSigns",
                    DecimalFormatSymbols.getInstance(Locale.ENGLISH)
                )
                decimalFormat.roundingMode = RoundingMode.DOWN
                return decimalFormat.format(this)

            } else {
                return when {
                    this.equals(0.0) -> "0.00"
                    this < 1.0 && this > 0 -> DecimalFormat(
                        "0.000#####",
                        DecimalFormatSymbols.getInstance(Locale.ENGLISH)
                    )
                        .format(this)
                    else -> DecimalFormat(
                        "#,###.0#######",
                        DecimalFormatSymbols.getInstance(Locale.ENGLISH)
                    ).format(this)
                }
            }
        } else {
            val decimalFormat = DecimalFormat(
                "#,###",
                DecimalFormatSymbols.getInstance(Locale.ENGLISH)
            )
            decimalFormat.roundingMode = RoundingMode.DOWN
            return decimalFormat.format(this)
        }
    }

    private fun generateNumberSigns(n: Int = 0): String {
        var s = ""
        for (i in 0 until n) {
            s += "#"
        }
        return s
    }

}