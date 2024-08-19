package com.miracle.common.utils

fun String.removeNewlines() = this.replace(Regex("\\n+"), " ")


fun String.formatPhoneNumber( ): String {
    if (length != 12) return this

    val regex = "(\\d{3})(\\d{2})(\\d{3})(\\d{2})(\\d{2})".toRegex()

    val matchResult = regex.find(this)
    return if (matchResult != null) {
        val (countryCode, areaCode, firstPart, secondPart, thirdPart) = matchResult.destructured
        "+$countryCode ($areaCode) $firstPart $secondPart $thirdPart"
    } else {
        this
    }
}