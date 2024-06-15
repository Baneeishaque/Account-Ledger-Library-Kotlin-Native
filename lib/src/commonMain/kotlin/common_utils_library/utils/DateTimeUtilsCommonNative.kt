package common_utils_library.utils

import korlibs.time.DateFormat
import korlibs.time.PatternDateFormat
import kotlin.jvm.JvmStatic

object DateTimeUtilsCommonNative {

    @JvmStatic
    val normalDatePattern: PatternDateFormat = DateFormat(pattern = "dd/MM/yyyy")
}
