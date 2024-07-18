package account_ledger_library.utils

import account_ledger_library.constants.ConstantsNative
import account_ledger_library.models.*
import common_utils_library.utils.GistUtilsCommonNative
import common_utils_library.utils.GistUtilsInteractiveCommonNative
import common_utils_library.utils.DateTimeUtilsCommonNative
import io.ktor.client.*
import io.ktor.utils.io.core.*
import korlibs.time.Date
import korlibs.time.DateTime
import korlibs.time.parseDate
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class GistUtilsInteractiveNative {

    fun processGistIdForDataV2(

        userName: String,
        userId: UInt,
        gitHubAccessToken: String,
        gistId: String,
        isDevelopmentMode: Boolean,
        isApiCall: Boolean = true,
        isVersion3: Boolean = false

    ): AccountLedgerGistModelV2 {

        val accountLedgerGistV2 = AccountLedgerGistModelV2(

            userName = userName,
            userId = userId,
            accountLedgerPages = LinkedHashMap()
        )
        runBlocking {

            GistUtilsCommonNative.getHttpClientForGitHub(

                accessToken = gitHubAccessToken,
                isDevelopmentMode = isDevelopmentMode

            ).use { client: HttpClient ->

                // TODO: inline use of serialization : for file name in gist
                val gistContent: String = GistUtilsInteractiveCommonNative.getGistContents(

                    client = client,
                    gistId = gistId,
                    isDevelopmentMode = isDevelopmentMode

                ).files.mainTxt.content

                val gistContentLines: List<String> = gistContent.lines()

                var currentAccountId = 0u
                var extractedLedger: LinkedHashMap<UInt, MutableList<String>> = LinkedHashMap()

                var isExecutionSuccess = true
                gistContentLines.forEach { currentLine: String ->

                    if (isExecutionSuccess) {

                        if (isDevelopmentMode) {

                            println(message = "Current line = $currentLine")
                            println(message = "currentAccountId = $currentAccountId")
                        }

                        if (currentLine.isNotEmpty() && (!currentLine.startsWith(prefix = "#")) && (currentLine.contains(

                                char = ConstantsNative.ACCOUNT_ID_PREFIX
                            ))
                        ) {
                            currentAccountId =
                                currentLine.substring(

                                    startIndex = currentLine.indexOf(ConstantsNative.ACCOUNT_ID_PREFIX) + 1,
                                    endIndex = currentLine.indexOf(
                                        ConstantsNative.ACCOUNT_ID_SUFFIX
                                    )
                                ).toUInt()
                            if (currentAccountId == 0U) {

                                val errorMessage =
                                    "Error : Account ID Must be a positive number, please correct it in your Gist Text for A/C {${
                                        currentLine.substring(

                                            startIndex = 0,
                                            endIndex = currentLine.indexOf(

                                                ConstantsNative.ACCOUNT_ID_PREFIX
                                            )
                                        ).trim()
                                    }}"

                                if (isApiCall) {
                                    //TODO : Create Common Utils for Multiplatform,
                                    // Depend on it to use ApiErrorMessage function
                                    if (isDevelopmentMode) {

                                        println(message = errorMessage)
                                    }
                                } else {

                                    println(message = errorMessage)
                                }
                                isExecutionSuccess = false

                            } else {
                                extractedLedger = TextAccountLedgerUtils.addLineToCurrentAccountLedger(

                                    ledgerToProcess = extractedLedger,
                                    desiredAccountId = currentAccountId,
                                    desiredLine = currentLine.substring(

                                        startIndex = currentLine.indexOf(

                                            char = ConstantsNative.ACCOUNT_ID_SUFFIX

                                        ) + 2
                                    )
                                )
                            }
                        } else {

                            if (currentLine.isNotEmpty() && (!currentLine.startsWith(prefix = "#"))) {

                                extractedLedger = TextAccountLedgerUtils.addLineToCurrentAccountLedger(

                                    ledgerToProcess = extractedLedger,
                                    desiredAccountId = currentAccountId,
                                    desiredLine = currentLine
                                )
                            }
                        }
                    }
                }

                if (isExecutionSuccess) {
                    extractedLedger.forEach { (localCurrentAccountId: UInt, currentAccountLedgerLines: List<String>) ->

                        accountLedgerGistV2.accountLedgerPages[localCurrentAccountId] = LinkedHashMap()

                        var isNextLineFinalBalance = false
                        var previousDate: Date = DateTime.now().date
                        var isFinalBalanceWritten = false
                        var finalBalanceWrittenDate: Date = DateTime.now().date

                        currentAccountLedgerLines.forEach { ledgerLine: String ->

                            if (ledgerLine.first() != ConstantsNative.DATE_UNDERLINE_CHARACTER) {

                                if (ledgerLine.first() == ConstantsNative.FINAL_BALANCE_PREFIX_CHARACTER) {

                                    isNextLineFinalBalance = true

                                } else if (isNextLineFinalBalance) {

                                    if (isDevelopmentMode) {

                                        println("ledgerLine = $ledgerLine")
                                        if (ledgerLine.trim().contains(char = ' ')) {

                                            val endIndex: Int = ledgerLine.indexOf(char = ' ')
                                            println("endIndex = $endIndex")
                                            println("actual value : ${ledgerLine.substring(0, endIndex)}")
                                        }
                                    }

                                    val finalBalance: Double = (
                                            if (ledgerLine.trim().contains(char = ' '))
                                                ledgerLine.substring(0, ledgerLine.indexOf(char = ' '))
                                            else ledgerLine).toDouble()
                                    val transactionDateAsText: String =
                                        previousDate.format(DateTimeUtilsCommonNative.normalDatePattern)

                                    accountLedgerGistV2.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText]!!.finalBalanceOnDate =
                                        finalBalance
                                    isFinalBalanceWritten = true
                                    finalBalanceWrittenDate = previousDate

                                    isNextLineFinalBalance = false

                                } else {

                                    val ledgerLineContents: List<String> = ledgerLine.split(" ", limit = 2)
                                    val dateOrAmount: String = ledgerLineContents.first()
                                    try {
                                        val transactionDate: Date =
                                            DateTimeUtilsCommonNative.normalDatePattern.parseDate(str = dateOrAmount)
                                        val transactionDateAsText: String =
                                            transactionDate.format(DateTimeUtilsCommonNative.normalDatePattern)
                                        if (ledgerLineContents.size > 1) {

                                            val initialBalanceOnDate: Double = ledgerLineContents.last().toDouble()
                                            accountLedgerGistV2.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText] =
                                                AccountLedgerGistDateLedgerModel(
                                                    initialBalanceOnDate = initialBalanceOnDate,
                                                    transactionsOnDate = mutableListOf()
                                                )
                                        } else {

                                            accountLedgerGistV2.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText] =
                                                AccountLedgerGistDateLedgerModel(
                                                    transactionsOnDate = mutableListOf()
                                                )
                                        }
                                        previousDate = transactionDate

                                    } catch (_: Exception) {

                                        val transactionDateAsText: String =
                                            previousDate.format(DateTimeUtilsCommonNative.normalDatePattern)

                                        val transactionParticulars: String
                                        val transactionAmount: Double
                                        if (isVersion3) {

                                            val transactionContents: List<String> = ledgerLineContents.last().split(

                                                " ",
                                                limit = 2
                                            )
                                            transactionAmount = transactionContents.first().toDouble()
                                            transactionParticulars =
                                                "${ledgerLineContents.first()} ${transactionContents.last()}"

                                        } else {

                                            transactionAmount = if (dateOrAmount == "0") 0.0
                                            else (if (dateOrAmount.contains(char = '+')) dateOrAmount.toDouble()
                                            else (if (dateOrAmount.contains(char = '-')) dateOrAmount.toDouble()
                                            else -(dateOrAmount.toDouble())))
                                            transactionParticulars = ledgerLineContents[1]
                                        }
                                        accountLedgerGistV2.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText]!!.transactionsOnDate.add(

                                            AccountLedgerGistTransactionModel(

                                                transactionParticulars = transactionParticulars,
                                                transactionAmount = transactionAmount
                                            )
                                        )

                                        if (isFinalBalanceWritten && (finalBalanceWrittenDate == previousDate)) {

                                            accountLedgerGistV2.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText]!!.finalBalanceOnDate =
                                                null
                                            isFinalBalanceWritten = false
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (isApiCall) {

                    val accountLedgerGistAccounts: MutableList<AccountLedgerGistAccountModel> = mutableListOf()

                    if (isExecutionSuccess) {
                        accountLedgerGistV2.accountLedgerPages.forEach { accountLedgerGistIdPage: Map.Entry<UInt, LinkedHashMap<String, AccountLedgerGistDateLedgerModel>> ->

                            val accountLedgerGistDateLedgersForJson: MutableList<AccountLedgerGistDateLedgerModelForJson> =
                                mutableListOf()

                            accountLedgerGistIdPage.value.forEach { accountLedgerGistDatePage: Map.Entry<String, AccountLedgerGistDateLedgerModel> ->

                                accountLedgerGistDateLedgersForJson.add(
                                    AccountLedgerGistDateLedgerModelForJson(
                                        accountLedgerPageDate = accountLedgerGistDatePage.key,
                                        initialBalanceOnDate = accountLedgerGistDatePage.value.initialBalanceOnDate,
                                        transactionsOnDate = accountLedgerGistDatePage.value.transactionsOnDate,
                                        finalBalanceOnDate = accountLedgerGistDatePage.value.finalBalanceOnDate
                                    )
                                )
                            }

                            accountLedgerGistAccounts.add(
                                AccountLedgerGistAccountModel(
                                    accountId = accountLedgerGistIdPage.key,
                                    accountLedgerDatePages = accountLedgerGistDateLedgersForJson
                                )
                            )
                        }
                    }
                    print(
                        Json.encodeToString(
                            serializer = AccountLedgerGistModelForJson.serializer(),
                            value = AccountLedgerGistModelForJson(
                                userName = userName,
                                userId = userId,
                                accountLedgerPages = accountLedgerGistAccounts
                            )
                        )
                    )
                }
            }
        }
        return accountLedgerGistV2
    }

    fun processGistIdForDataV3(

        userName: String,
        userId: UInt,
        gitHubAccessToken: String,
        gistId: String,
        isDevelopmentMode: Boolean,
        isApiCall: Boolean = true,
        isVersion3: Boolean = false

    ): AccountLedgerGistModelV3 {

        val accountLedgerGistV2: AccountLedgerGistModelV2 = processGistIdForDataV2(

            userName = userName,
            userId = userId,
            gitHubAccessToken = gitHubAccessToken,
            gistId = gistId,
            isDevelopmentMode = isDevelopmentMode,
            isApiCall = false,
            isVersion3 = isVersion3
        )
        if (isDevelopmentMode) {

            println(
                "Gist Data : ${

                    Json.encodeToString(

                        serializer = AccountLedgerGistModelV2.serializer(),
                        value = accountLedgerGistV2
                    )
                }"
            )
        }

        val accountLedgerGistV3 = AccountLedgerGistModelV3(

            userName = accountLedgerGistV2.userName,
            userId = userId,
            accountLedgerPages = mutableListOf()
        )

        accountLedgerGistV2.accountLedgerPages.forEach { accountLedgerPage: Map.Entry<UInt, LinkedHashMap<String, AccountLedgerGistDateLedgerModel>> ->

            val localAccountLedgerPage = AccountLedgerPage(
                accountId = accountLedgerPage.key,
                transactionDatePages = mutableListOf()
            )

            accountLedgerPage.value.forEach { transactionDatePage: Map.Entry<String, AccountLedgerGistDateLedgerModel> ->

                localAccountLedgerPage.transactionDatePages.add(

                    TransactionDatePage(

                        transactionDatePage.key,
                        transactionDatePage.value.initialBalanceOnDate,
                        transactionDatePage.value.transactionsOnDate,
                        transactionDatePage.value.finalBalanceOnDate,
                    )
                )
            }

            accountLedgerGistV3.accountLedgerPages.add(localAccountLedgerPage)
        }
        if (isApiCall) {

            println(

                message = Json.encodeToString(

                    serializer = AccountLedgerGistModelV3.serializer(),
                    value = accountLedgerGistV3
                )
            )
        }
        return accountLedgerGistV3
    }

    fun processGistIdForDataV3ToV4(

        userName: String,
        userId: UInt,
        gitHubAccessToken: String,
        gistId: String,
        isDevelopmentMode: Boolean,
        dummy: Boolean = true,
        dummy2: Boolean = true
    ) {

        val accountLedgerGistV3: AccountLedgerGistModelV3 = processGistIdForDataV3(

            userName = userName,
            userId = userId,
            gitHubAccessToken = gitHubAccessToken,
            gistId = gistId,
            isDevelopmentMode = isDevelopmentMode,
            isApiCall = false,
            isVersion3 = true
        )

        for ((accountId: UInt, transactionDatePages: MutableList<TransactionDatePage>) in accountLedgerGistV3.accountLedgerPages) {

            for ((transactionDate: String, _: Double?, transactions: List<AccountLedgerGistTransactionModel>?, _: Double?) in transactionDatePages) {

                if (transactions != null) {

                    for ((transactionParticulars: String, transactionAmount: Double) in transactions) {

                        val transactionParticularsContents: List<String> = transactionParticulars.split(" ", limit = 2)
                        println(

                            message = "$transactionDate ${transactionParticularsContents.first()} [$accountId] [${
                                transactionParticularsContents.last()
                                    .substringAfter(delimiter = ConstantsNative.GIST_V3_TO_ACCOUNT_ID_SEPARATOR).trim()
                            }] $transactionAmount ${
                                transactionParticularsContents.last()
                                    .substringBefore(delimiter = ConstantsNative.GIST_V3_TO_ACCOUNT_ID_SEPARATOR).trim()
                            }"
                        )
                    }
                }
            }
        }
    }
}
