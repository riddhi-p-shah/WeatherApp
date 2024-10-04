package com.riddhi.weatherforecast.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UtilsTest {

    @Test
    fun testGet12hFormattedTime_ValidTimestampPositiveOffset() {
        //arrange
        val timestamp = 1728046466L
        val offsetSeconds = 19800
        val expectedTime = "06:24 PM"

        //act
        val actualTime = Utils.get12hFormattedTime(timestamp, offsetSeconds)

        //assert
        assertEquals(expectedTime, actualTime)
    }

    @Test
    fun testGet12hFormattedTime_ValidTimestampNegativeOffset() {
        //arrange
        val timestamp = 1727952904L
        val offsetSeconds = -14400
        val expectedTime = "06:55 AM"

        //act
        val actualTime = Utils.get12hFormattedTime(timestamp, offsetSeconds)

        //assert
        assertEquals(expectedTime, actualTime)
    }

    @Test
    fun testGet12hFormattedTime_InvalidTimestamp() {
        //arrange
        val timestamp: Long? = null
        //act
        val actualTime = Utils.get12hFormattedTime(timestamp)
        //assert
        assertNull(actualTime)
//        assertNull(actualTime)
    }
}