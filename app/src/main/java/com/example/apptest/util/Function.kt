package com.example.apptest.util
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TRY CATCH ---------------------------------------------------------------------------------------

/**
 * Convenience function used to run a [block] of code that can throw [Exception]
 *
 * When [Exception] was threw, caller only want to log that [Exception] and return [default]
 */
fun <T : Any> tryCatchLog(default: T, block: () -> T): T =
    try {
        block()
    } catch (cause: Throwable) {
        Timber.e(cause)
        default
    }

/**
 * Like [tryCatchLog] but can use with nullable value
 */
fun <T : Any> tryCatchLogNullable(default: T?, block: () -> T?): T? =
    try {
        block()
    } catch (cause: Throwable) {
        Timber.e(cause)
        default
    }

suspend fun <T> tryCatchLogSuspendNullable(default: T?, block: suspend () -> T?): T? =
    try {
        block()
    } catch (cause: Throwable) {
        Timber.e(cause)
        default
    }

/**
 * Converts a timestamp (milliseconds) to a date string in the format MM/dd/yyyy.
 *
 * @param millis The timestamp in milliseconds.
 * @return A formatted date string in MM/dd/yyyy format.
 */
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

/**
 * Converts a timestamp (milliseconds) to a formatted date string.
 *
 * @param timestamp The timestamp in milliseconds.
 * @param format The desired date format (default is "dd/MM/yyyy").
 * @return A formatted date string based on the provided format.
 */
fun convertTimestampToDate(timestamp: Long, format: String = "dd/MM/yyyy"): String {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(Date(timestamp * 1000))
}
