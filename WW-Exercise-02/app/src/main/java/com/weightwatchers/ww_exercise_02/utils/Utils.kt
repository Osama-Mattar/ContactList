package com.weightwatchers.ww_exercise_02.utils

import java.util.regex.Pattern

fun isValidMsisdn(msisdn: String): Boolean {
    val pattern = ("^\\(?(\\d{3})\\)?[-. ]?(\\d{3})[-. ]?(\\d{4})$")
    return Pattern.compile(pattern).matcher(msisdn).matches()
}