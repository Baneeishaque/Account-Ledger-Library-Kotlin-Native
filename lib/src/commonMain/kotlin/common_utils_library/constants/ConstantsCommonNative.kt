package common_utils_library.constants

object ConstantsCommonNative {

    //    TODO : Control width via function parameter
    const val dashedLineSeparator: String = "---------------------------------------------------"
    const val notImplementedMessage = "Not Implemented Yet..."

    //Needs entire match
    const val railwayTimeRegexPattern: String = "(((2[0-3]|[01]?[0-9])(:[0-5][0-9]?(:[0-5][0-9]?)?)?)?)"

    const val digitIncrementOrDecrementRegexPattern:String = "(\\d+)?([-+])"

    const val defaultTimeResetHour = 9
    const val defaultTimeResetMinute = 0
    const val defaultTimeResetSecond = 0
}
