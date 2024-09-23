package com.riddhi.weatherforecast.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Utils {
    companion object {
        fun get12hFormattedTime(timestamp: Long?, timezoneOffsetHours: Int = 0): String? {
            if(timestamp == null) return null
            try{
                val timeZone = ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezoneOffsetHours))
                val instant = Instant.ofEpochSecond(timestamp)
                val localDateTime = instant.atZone(timeZone)

                return localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
            }catch (e:Exception){
                print(e.message)
                return null
            }
        }
    }
}