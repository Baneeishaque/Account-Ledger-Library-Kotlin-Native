package account_ledger_library.constants

import account_ledger_library.enums.BajajRewardTypeEnum
import common_utils_library.constants.ConstantsCommonNative
import kotlin.jvm.JvmStatic


object ConstantsNative {

    const val TRANSACTION_TEXT: String = "Transaction"
    const val WALLET_TEXT: String = "Wallet"
    const val ACCOUNT_TEXT: String = "Account"
    const val SPECIAL_TEXT: String = "Special"
    const val USER_TEXT: String = "User"
    const val ID_TEXT: String = "ID"
    const val BAJAJ_TEXT = "Bajaj"

    const val TYPE_TEXT: String = "Type"
    const val SPECIAL_TRANSACTION_TYPE_TEXT: String = "$SPECIAL_TEXT $TRANSACTION_TEXT $TYPE_TEXT"

    const val DEFAULT_VALUE_FOR_STRING_ENVIRONMENT_VARIABLES: String = "N/A"
    const val DEFAULT_VALUE_FOR_INTEGER_ENVIRONMENT_VARIABLES: Int = 0

    const val FREQUENCY_OF_ACCOUNTS_FILE_NAME: String = "frequencyOf${ACCOUNT_TEXT}s.json"
    val SPECIAL_TRANSACTION_TYPES_FILE_NAME: String = "${SPECIAL_TEXT.lowercase()}${TRANSACTION_TEXT}${TYPE_TEXT}s.json"

    const val ACCOUNT_HEADER_IDENTIFIER: String = "A/C Ledger "
    const val WALLET_ACCOUNT_HEADER_IDENTIFIER: String = WALLET_TEXT

    //    internal const val BANK_ACCOUNT_HEADER_IDENTIFIER: String = "Bank"
    const val BANK_ACCOUNT_HEADER_IDENTIFIER: String = "PNB"

    const val ACCOUNT_HEADER_UNDERLINE_CHARACTER: Char = '~'
    const val ACCOUNT_BALANCE_HOLDER_OPENING_BRACE: Char = '{'
    const val DATE_UNDERLINE_CHARACTER: Char = '~'
    const val FINAL_BALANCE_PREFIX_CHARACTER: Char = '='

    const val TIME_RESET_COMMAND_INDICATOR: String = "Tr"
    val timeResetPatternRegex: Regex =
        Regex(pattern = "$TIME_RESET_COMMAND_INDICATOR${ConstantsCommonNative.RAILWAY_TIME_REGEX_PATTERN}")

    const val HOUR_INCREMENT_OR_DECREMENT_COMMAND_INDICATOR: String = "H"
    val hourIncrementOrDecrementPatternRegex: Regex =
        Regex(pattern = "$HOUR_INCREMENT_OR_DECREMENT_COMMAND_INDICATOR${ConstantsCommonNative.DIGIT_INCREMENT_OR_DECREMENT_REGEX_PATTERN}")

    const val MINUTE_INCREMENT_OR_DECREMENT_COMMAND_INDICATOR: String = "M"
    val minuteIncrementOrDecrementPatternRegex: Regex =
        Regex(pattern = "$MINUTE_INCREMENT_OR_DECREMENT_COMMAND_INDICATOR${ConstantsCommonNative.DIGIT_INCREMENT_OR_DECREMENT_REGEX_PATTERN}")

    const val SECOND_INCREMENT_OR_DECREMENT_COMMAND_INDICATOR: String = "S"
    val secondIncrementOrDecrementPatternRegex: Regex =
        Regex(pattern = "$SECOND_INCREMENT_OR_DECREMENT_COMMAND_INDICATOR${ConstantsCommonNative.DIGIT_INCREMENT_OR_DECREMENT_REGEX_PATTERN}")

    const val DAY_INCREMENT_OR_DECREMENT_COMMAND_INDICATOR: String = "D"
    val dayIncrementOrDecrementPatternRegex: Regex =
        Regex(pattern = "$DAY_INCREMENT_OR_DECREMENT_COMMAND_INDICATOR${ConstantsCommonNative.DIGIT_INCREMENT_OR_DECREMENT_REGEX_PATTERN}")

    val dayIncrementOrDecrementWithTimeResetPatternRegex: Regex =
        Regex(pattern = "($DAY_INCREMENT_OR_DECREMENT_COMMAND_INDICATOR${ConstantsCommonNative.DIGIT_INCREMENT_OR_DECREMENT_REGEX_PATTERN})($TIME_RESET_COMMAND_INDICATOR${ConstantsCommonNative.RAILWAY_TIME_REGEX_PATTERN})")

    const val ACCOUNT_ID_PREFIX = '['
    const val ACCOUNT_ID_SUFFIX = ']'

    val BAJAJ_COINS_TEXT = "$BAJAJ_TEXT-${BajajRewardTypeEnum.COINS.value}"
    val BAJAJ_COINS_INCOME_TEXT: String = generateIncomeText(accountSpecifier = BAJAJ_COINS_TEXT)
    val BAJAJ_COINS_INCOME_ACCOUNT_ID_TEXT: String = generateAccountIdText(accountSpecifier = BAJAJ_COINS_INCOME_TEXT)
    val BAJAJ_COINS_WALLET_TEXT = "$BAJAJ_COINS_TEXT-Wallet"
    val BAJAJ_COINS_WALLET_ACCOUNT_ID_TEXT: String = generateAccountIdText(accountSpecifier = BAJAJ_COINS_WALLET_TEXT)
    val BAJAJ_COINS_CONVERSION_RATE_TEXT = "$BAJAJ_COINS_TEXT-Conversion-Rate"

    val BAJAJ_CASHBACK_TEXT = "$BAJAJ_TEXT-${BajajRewardTypeEnum.CASHBACK.value}"
    val BAJAJ_CASHBACK_INCOME_TEXT: String = generateIncomeText(accountSpecifier = BAJAJ_CASHBACK_TEXT)
    val BAJAJ_CASHBACK_INCOME_ACCOUNT_ID_TEXT: String =
        generateAccountIdText(accountSpecifier = BAJAJ_CASHBACK_INCOME_TEXT)
    val BAJAJ_CASHBACK_ACCOUNT_ID_TEXT: String = generateAccountIdText(accountSpecifier = BAJAJ_CASHBACK_TEXT)

    const val USER_CANCELLED_TRANSACTION_TEXT = "${ConstantsCommonNative.USER_CANCELLED_TEXT} $TRANSACTION_TEXT"
    const val DATE_FROM_USERNAME_ERROR = "${ConstantsCommonNative.ERROR_TEXT}: Can't derive date from username..."

    const val BALANCE_TEXT = "Balance"
    const val WITHOUT_BALANCE_CHECK_TEXT = "(${ConstantsCommonNative.WITHOUT_TEXT} $BALANCE_TEXT Check)"
    const val BALANCE_CALCULATION_ERROR_TEXT = "$BALANCE_TEXT Calculation ${ConstantsCommonNative.ERROR_TEXT}"

    const val WITHOUT_FUNDING_TRANSACTION_TEXT = "(${ConstantsCommonNative.WITHOUT_TEXT} Funding $TRANSACTION_TEXT)"

    const val ACCOUNT_ID_FORMAL_NAME: String = "$ACCOUNT_TEXT Index No."

    @JvmStatic
    fun generateAccountIdText(accountSpecifier: String): String {

        return "$accountSpecifier-$ACCOUNT_TEXT $ID_TEXT"
    }

    @JvmStatic
    fun generateIncomeText(accountSpecifier: String): String {

        return "$accountSpecifier-Income"
    }
}
