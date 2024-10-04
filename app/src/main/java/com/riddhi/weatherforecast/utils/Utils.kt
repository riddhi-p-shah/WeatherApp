package com.riddhi.weatherforecast.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Utility class for handling misc operations.
 */
class Utils {
    companion object {

        /**
         * Converts a timestamp to a 12-hour formatted time string using the specified timezone offset.
         *
         * @param timestamp The timestamp to convert.
         * @param timezoneOffsetSeconds The timezone offset in seconds.
         * @return The formatted time string in 12-hour format, or null if the timestamp is invalid.
         */
        fun get12hFormattedTime(timestamp: Long?, timezoneOffsetSeconds: Int = 0): String? {
            if(timestamp == null) return null
            try{
                // Create a ZoneId object for the specified timezone offset
                val timeZone = ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezoneOffsetSeconds))

                // Convert the timestamp to an Instant object
                val instant = Instant.ofEpochSecond(timestamp)

                // Convert the Instant to a LocalDateTime object using the specified timezone
                val localDateTime = instant.atZone(timeZone)

                // Format and return the LocalDateTime as a 12-hour time string
                return localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
            }catch (e:Exception){
                // Handle any exceptions that may occur during the conversion process
                print(e.message)
                return null
            }
        }
    }
}