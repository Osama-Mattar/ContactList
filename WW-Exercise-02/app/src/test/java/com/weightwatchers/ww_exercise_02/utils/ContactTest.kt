package com.weightwatchers.ww_exercise_02.utils

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ContactTest {
    @Test
    fun testMsisdnValidation() {
        assertThat(isValidMsisdn("(781) 266-8581"), `is`(true))
    }
}