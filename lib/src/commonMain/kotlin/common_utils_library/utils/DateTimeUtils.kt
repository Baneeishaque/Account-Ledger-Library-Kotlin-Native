package common_utils_library.utils

import com.soywiz.klock.DateFormat
import com.soywiz.klock.PatternDateFormat

//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//import java.time.format.DateTimeFormatter.ofPattern
//import java.time.format.DateTimeParseException

object DateTimeUtils {

    //    @JvmStatic
    val normalDateTimePatternAsText = "dd/MM/yyyy HH:mm:ss"

//    @JvmStatic
//    val normalDateTimePattern: DateTimeFormatter = ofPattern(normalDateTimePatternAsText)!!

//    @JvmStatic
    val normalDatePattern: PatternDateFormat = DateFormat(pattern = "dd/MM/yyyy")

//    @JvmStatic
//    fun addDaysToNormalDateTimeInText(
//
//        dateTimeInText: String, days: Int
//
//    ): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = days,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun subtractDaysFromNormalDateTimeInText(
//
//        dateTimeInText: String, days: Int
//
//    ): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = days,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }
//
//    @JvmStatic
//    fun add5MinutesToNormalDateTimeInText(
//
//        dateTimeInText: String
//
//    ): String {
//
//        return addMinutesToNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            minutes = 5
//        )
//    }

//    @JvmStatic
//    fun addHoursToNormalDateTimeInText(
//
//        dateTimeInText: String,
//        hours: Int
//
//    ): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = hours,
//            timePartSpecification = TimePartSpecificationEnum.HOUR,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun addMinutesToNormalDateTimeInText(
//
//        dateTimeInText: String,
//        minutes: Int
//
//    ): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = minutes,
//            timePartSpecification = TimePartSpecificationEnum.MINUTE,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun addSecondsToNormalDateTimeInText(
//
//        dateTimeInText: String,
//        seconds: Int
//
//    ): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = seconds,
//            timePartSpecification = TimePartSpecificationEnum.SECOND,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }

//    @JvmStatic
//    fun addDaysToNormalDateTimeInTextAsDateTime(
//
//        dateTimeInText: String,
//        days: Int
//
//    ): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = days,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun resetTimeOnNormalDateTimeInTextToXAsDateTime(
//
//        dateTimeInText: String,
//        resetHour: Int = Constants.defaultTimeResetHour,
//        resetMinute: Int = Constants.defaultTimeResetMinute,
//        resetSecond: Int = Constants.defaultTimeResetSecond
//
//    ): LocalDateTime {
//
//        return resetTimeOnDateTimeInTextToXAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            dateTimePattern = normalDateTimePattern,
//            resetHour = resetHour,
//            resetMinute = resetMinute,
//            resetSecond = resetSecond
//        )
//    }
//
//    @JvmStatic
//    fun resetTimeOnDateTimeInTextToXAsDateTime(
//
//        dateTimeInText: String,
//        dateTimePattern: DateTimeFormatter,
//        resetHour: Int = Constants.defaultTimeResetHour,
//        resetMinute: Int = Constants.defaultTimeResetMinute,
//        resetSecond: Int = Constants.defaultTimeResetSecond
//
//    ): LocalDateTime {
//
//        return LocalDateTime.parse(dateTimeInText, dateTimePattern).withHour(resetHour).withMinute(resetMinute)
//            .withSecond(resetSecond)
//    }
//
//    @JvmStatic
//    fun resetTimeOnDateTimeInTextToX(
//
//        dateTimeInText: String,
//        dateTimePattern: DateTimeFormatter,
//        resetHour: Int = Constants.defaultTimeResetHour,
//        resetMinute: Int = Constants.defaultTimeResetMinute,
//        resetSecond: Int = Constants.defaultTimeResetSecond
//
//    ): String {
//
//        return resetTimeOnDateTimeInTextToXAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            dateTimePattern = dateTimePattern,
//            resetHour = resetHour,
//            resetMinute = resetMinute,
//            resetSecond = resetSecond
//
//        ).format(
//            /* formatter = */ dateTimePattern
//        )
//    }
//
//    @JvmStatic
//    fun resetTimeOnNormalDateTimeInTextToX(
//
//        dateTimeInText: String,
//        resetHour: Int = Constants.defaultTimeResetHour,
//        resetMinute: Int = Constants.defaultTimeResetMinute,
//        resetSecond: Int = Constants.defaultTimeResetSecond
//
//    ): String {
//
//        return resetTimeOnDateTimeInTextToX(
//
//            dateTimeInText = dateTimeInText,
//            dateTimePattern = normalDateTimePattern,
//            resetHour = resetHour,
//            resetMinute = resetMinute,
//            resetSecond = resetSecond
//        )
//    }
//
//    @JvmStatic
//    fun resetTimeOnDateTimeToX(
//
//        dateTime: LocalDateTime,
//        resetHour: Int = Constants.defaultTimeResetHour,
//        resetMinute: Int = Constants.defaultTimeResetMinute,
//        resetSecond: Int = Constants.defaultTimeResetSecond
//
//    ): LocalDateTime {
//
//        return dateTime.withHour(/* hour = */ resetHour)
//            .withMinute(/* minute = */ resetMinute)
//            .withSecond(/* second = */ resetSecond)
//    }
//
//    @JvmStatic
//    fun resetTimeOnDateTimeToXAsText(
//
//        dateTime: LocalDateTime,
//        dateTimePattern: DateTimeFormatter,
//        resetHour: Int = Constants.defaultTimeResetHour,
//        resetMinute: Int = Constants.defaultTimeResetMinute,
//        resetSecond: Int = Constants.defaultTimeResetSecond
//
//    ): String {
//
//        return resetTimeOnDateTimeToX(
//
//            dateTime = dateTime,
//            resetHour = resetHour,
//            resetMinute = resetMinute,
//            resetSecond = resetSecond
//
//        ).format(dateTimePattern)
//    }
//
//    @JvmStatic
//    fun resetTimeOnNormalDateTimeToXAsText(
//
//        dateTime: LocalDateTime,
//        resetHour: Int = Constants.defaultTimeResetHour,
//        resetMinute: Int = Constants.defaultTimeResetMinute,
//        resetSecond: Int = Constants.defaultTimeResetSecond
//
//    ): String {
//
//        return resetTimeOnDateTimeToXAsText(
//
//            dateTime = dateTime,
//            dateTimePattern = normalDateTimePattern,
//            resetHour = resetHour,
//            resetMinute = resetMinute,
//            resetSecond = resetSecond
//        )
//    }
//
//    @JvmStatic
//    fun subtractDaysFromNormalDateTimeInTextAsDateTime(
//
//        dateTimeInText: String,
//        days: Int
//
//    ): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = days,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }
//
//    @JvmStatic
//    fun addHoursToNormalDateTimeInTextAsDateTime(
//
//        dateTimeInText: String,
//        hours: Int
//
//    ): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = hours,
//            timePartSpecification = TimePartSpecificationEnum.HOUR,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun addMinutesToNormalDateTimeInTextAsDateTime(
//
//        dateTimeInText: String,
//        minutes: Int
//
//    ): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = minutes,
//            timePartSpecification = TimePartSpecificationEnum.MINUTE,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun addSecondsToNormalDateTimeInTextAsDateTime(
//
//        dateTimeInText: String,
//        seconds: Int
//
//    ): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = seconds,
//            timePartSpecification = TimePartSpecificationEnum.SECOND,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun subtractHoursFromNormalDateTimeInTextAsDateTime(
//
//        dateTimeInText: String,
//        hours: Int
//
//    ): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = hours,
//            timePartSpecification = TimePartSpecificationEnum.HOUR,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }
//
//    @JvmStatic
//    fun subtractMinutesFromNormalDateTimeInTextAsDateTime(
//
//        dateTimeInText: String,
//        minutes: Int
//
//    ): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = minutes,
//            timePartSpecification = TimePartSpecificationEnum.MINUTE,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }
//
//    @JvmStatic
//    fun subtractSecondsFromNormalDateTimeInTextAsDateTime(
//
//        dateTimeInText: String,
//        seconds: Int
//
//    ): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = seconds,
//            timePartSpecification = TimePartSpecificationEnum.SECOND,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }

//    @JvmStatic
//    fun subtractHoursFromNormalDateTimeInText(
//
//        dateTimeInText: String,
//        hours: Int
//
//    ): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = hours,
//            timePartSpecification = TimePartSpecificationEnum.HOUR,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }
//
//    @JvmStatic
//    fun subtractMinutesFromNormalDateTimeInText(
//
//        dateTimeInText: String,
//        minutes: Int
//
//    ): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = minutes,
//            timePartSpecification = TimePartSpecificationEnum.MINUTE,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }
//
//    @JvmStatic
//    fun subtractSecondsFromNormalDateTimeInText(
//
//        dateTimeInText: String,
//        seconds: Int
//
//    ): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = seconds,
//            timePartSpecification = TimePartSpecificationEnum.SECOND,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }

//    @JvmStatic
//    private fun manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//        dateTimeInText: String,
//        timePartQuantity: Int,
//        timePartSpecification: TimePartSpecificationEnum,
//        timePartManipulationActivity: TimePartManipulationEnum
//
//    ): LocalDateTime {
//
//        val result = LocalDateTime.parse(
//            /* text = */ dateTimeInText,
//            /* formatter = */ normalDateTimePattern
//        )
//        when (timePartManipulationActivity) {
//
//            TimePartManipulationEnum.ADDITION -> {
//
//                return when (timePartSpecification) {
//
//                    TimePartSpecificationEnum.HOUR -> result.plusHours(timePartQuantity.toLong())
//                    TimePartSpecificationEnum.MINUTE -> result.plusMinutes(timePartQuantity.toLong())
//                    TimePartSpecificationEnum.SECOND -> result.plusSeconds(timePartQuantity.toLong())
//                    TimePartSpecificationEnum.DAY -> result.plusDays(timePartQuantity.toLong())
//                }
//            }
//
//            TimePartManipulationEnum.SUBTRACTION -> {
//
//                return when (timePartSpecification) {
//
//                    TimePartSpecificationEnum.HOUR -> result.minusHours(timePartQuantity.toLong())
//                    TimePartSpecificationEnum.MINUTE -> result.minusMinutes(timePartQuantity.toLong())
//                    TimePartSpecificationEnum.SECOND -> result.minusSeconds(timePartQuantity.toLong())
//                    TimePartSpecificationEnum.DAY -> result.minusDays(timePartQuantity.toLong())
//                }
//            }
//        }
//    }
//
//    @JvmStatic
//    private fun manipulateTimePartOnNormalDateTimeInText(
//
//        dateTimeInText: String,
//        timePartQuantity: Int,
//        timePartSpecification: TimePartSpecificationEnum,
//        timePartManipulationActivity: TimePartManipulationEnum
//
//    ): String {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = timePartQuantity,
//            timePartSpecification = timePartSpecification,
//            timePartManipulationActivity = timePartManipulationActivity
//
//        ).format(/* formatter = */ normalDateTimePattern)
//    }
//
//    @JvmStatic
//    fun subtractSecondsFromMySqlDateTimeInTextAsDateTime(
//
//        dateTimeInText: String,
//        seconds: Int
//
//    ): LocalDateTime {
//
//        return LocalDateTime.parse(
//            dateTimeInText, MysqlUtils.mysqlDateTimePattern
//        ).minusSeconds(seconds.toLong())
//    }
//
//    @JvmStatic
//    fun subtractSecondsFromMySqlDateTimeInText(
//
//        dateTimeInText: String,
//        seconds: Int
//
//    ): String {
//
//        return subtractSecondsFromMySqlDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            seconds = seconds
//
//        ).format(MysqlUtils.mysqlDateTimePattern)
//    }

//    @JvmStatic
//    fun subtract1SecondFromMySqlDateTimeInText(
//
//        dateTimeInText: String
//
//    ): String {
//
//        return subtractSecondsFromMySqlDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            seconds = 1
//        )
//    }
//
//    @JvmStatic
//    fun add1DayToNormalDateTimeInText(dateTimeInText: String): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = 1,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun subtract1DayFromNormalDateTimeInText(dateTimeInText: String): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = 1,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }
//
//    @JvmStatic
//    fun add1DayToNormalDateTimeInTextAsDateTime(dateTimeInText: String): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = 1,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun subtract1DayFromNormalDateTimeInTextAsDateTime(dateTimeInText: String): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = 1,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }
//
//    @JvmStatic
//    fun add2DaysToNormalDateTimeInText(dateTimeInText: String): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = 2,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun subtract2DaysFromNormalDateTimeInText(dateTimeInText: String): String {
//
//        return manipulateTimePartOnNormalDateTimeInText(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = 2,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }
//
//    @JvmStatic
//    fun add2DaysToNormalDateTimeInTextAsDateTime(dateTimeInText: String): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = 2,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.ADDITION
//        )
//    }
//
//    @JvmStatic
//    fun subtract2DaysFromNormalDateTimeInTextAsDateTime(dateTimeInText: String): LocalDateTime {
//
//        return manipulateTimePartOnNormalDateTimeInTextAsDateTime(
//
//            dateTimeInText = dateTimeInText,
//            timePartQuantity = 2,
//            timePartSpecification = TimePartSpecificationEnum.DAY,
//            timePartManipulationActivity = TimePartManipulationEnum.SUBTRACTION
//        )
//    }
//
//    @JvmStatic
//    fun add1DayWith9ClockTimeToNormalDateTimeInText(dateTimeInText: String): String {
//
//        return add1DayWith9ClockTimeToNormalDateTimeInTextAsDateTime(dateTimeInText = dateTimeInText).format(
//            normalDateTimePattern
//        )
//    }
//
//    @JvmStatic
//    fun subtract1DayWith9ClockTimeToNormalDateTimeInText(dateTimeInText: String): String {
//
//        return subtract1DayWith9ClockTimeToNormalDateTimeInTextAsDateTime(dateTimeInText = dateTimeInText).format(
//            normalDateTimePattern
//        )
//    }
//
//    @JvmStatic
//    fun add1DayWith9ClockTimeToNormalDateTimeInTextAsDateTime(dateTimeInText: String): LocalDateTime {
//
//        return resetTimeOnDateTimeToX(dateTime = add1DayToNormalDateTimeInTextAsDateTime(dateTimeInText = dateTimeInText))
//    }
//
//    @JvmStatic
//    fun subtract1DayWith9ClockTimeToNormalDateTimeInTextAsDateTime(dateTimeInText: String): LocalDateTime {
//
//        return resetTimeOnDateTimeToX(dateTime = subtract1DayFromNormalDateTimeInTextAsDateTime(dateTimeInText = dateTimeInText))
//    }
//
//    @JvmStatic
//    fun add2DaysWith9ClockTimeToNormalDateTimeInTextAsDateTime(dateTimeInText: String): LocalDateTime {
//
//        return resetTimeOnDateTimeToX(dateTime = add2DaysToNormalDateTimeInTextAsDateTime(dateTimeInText = dateTimeInText))
//    }
//
//    @JvmStatic
//    fun subtract2DaysWith9ClockTimeFromNormalDateTimeInTextAsDateTime(dateTimeInText: String): LocalDateTime {
//
//        return resetTimeOnDateTimeToX(dateTime = subtract2DaysFromNormalDateTimeInTextAsDateTime(dateTimeInText = dateTimeInText))
//    }
//
//    @JvmStatic
//    fun add2DaysWith9ClockTimeToNormalDateTimeInText(dateTimeInText: String): String {
//
//        return add2DaysWith9ClockTimeToNormalDateTimeInTextAsDateTime(dateTimeInText = dateTimeInText).format(
//            normalDateTimePattern
//        )
//    }
//
//    fun subtract2DaysWith9ClockTimeFromNormalDateTimeInText(dateTimeInText: String): String {
//
//        return subtract2DaysWith9ClockTimeFromNormalDateTimeInTextAsDateTime(dateTimeInText = dateTimeInText).format(
//            normalDateTimePattern
//        )
//    }
//
//    @JvmStatic
//    fun getCurrentNormalDateTimeInText(): String {
//
//        return LocalDateTime.now().format(normalDateTimePattern)
//    }
//
//    fun normalDateTimeInTextToDateTime(
//
//        normalDateTimeInText: String,
//        conversionSuccessActions: () -> Unit = fun() {},
//        conversionFailureActions: (DateTimeParseException) -> Unit = fun(_) {}
//
//    ): IsOkModel<LocalDateTime> {
//
//        return dateTimeInTextToDateTime(
//
//            dateTimeInText = normalDateTimeInText,
//            dateTimeTextPattern = normalDateTimePattern,
//            conversionSuccessActions = conversionSuccessActions,
//            conversionFailureActions = conversionFailureActions
//        )
//    }
//
//    fun normalDateTimeInTextToDateTimeInteractive(
//
//        normalDateTimeInText: String,
//        conversionSuccessActions: () -> Unit = fun() {},
//        conversionFailureActions: () -> Unit = fun() {}
//
//    ): IsOkModel<LocalDateTime> {
//
//        return dateTimeInTextToDateTimeInteractive(
//
//            dateTimeInText = normalDateTimeInText,
//            dateTimeTextPattern = normalDateTimePattern,
//            conversionSuccessActions = conversionSuccessActions,
//            conversionFailureActions = conversionFailureActions
//        )
//    }
//
//    fun dateTimeInTextToDateTime(
//
//        dateTimeInText: String,
//        dateTimeTextPattern: DateTimeFormatter,
//        conversionSuccessActions: () -> Unit = fun() {},
//        conversionFailureActions: (DateTimeParseException) -> Unit = fun(_: DateTimeParseException) {}
//
//    ): IsOkModel<LocalDateTime> {
//
//        return try {
//
//            val result: LocalDateTime = LocalDateTime.parse(dateTimeInText, dateTimeTextPattern)
//            conversionSuccessActions.invoke()
//            IsOkModel(isOK = true, data = result)
//
//        } catch (dateTimeParseException: DateTimeParseException) {
//
//            conversionFailureActions.invoke(dateTimeParseException)
//            IsOkModel(isOK = false)
//        }
//    }
//
//    @JvmStatic
//    fun dateTimeInTextToDateTimeInteractive(
//
//        dateTimeInText: String,
//        dateTimeTextPattern: DateTimeFormatter,
//        conversionSuccessActions: () -> Unit = fun() {},
//        conversionFailureActions: () -> Unit = fun() {}
//
//    ): IsOkModel<LocalDateTime> {
//
//        return dateTimeInTextToDateTime(
//
//            dateTimeInText,
//            dateTimeTextPattern,
//            conversionSuccessActions,
//            fun(dateTimeParseException) {
//
//                println("Date Error : ${dateTimeParseException.localizedMessage}")
//                conversionFailureActions.invoke()
//            },
//        )
//    }
}
