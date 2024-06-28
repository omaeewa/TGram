package com.miracle.common.utils

fun String.removeNewlines() = this.replace(Regex("\\n+"), " ")


