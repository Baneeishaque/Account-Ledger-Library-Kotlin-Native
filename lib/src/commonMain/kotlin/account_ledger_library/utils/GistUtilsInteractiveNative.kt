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

    fun processGistIdForData(

        userName: String,
        userId: UInt,
        gitHubAccessToken: String,
        gistId: String,
        isDevelopmentMode: Boolean,
        isApiCall: Boolean = true,
        isVersion3: Boolean = false

    ): AccountLedgerGistModel {

        val accountLedgerGist = AccountLedgerGistModel(

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

                        if (currentLine.contains(char = ConstantsNative.ACCOUNT_ID_PREFIX)) {
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

                        accountLedgerGist.accountLedgerPages[localCurrentAccountId] = LinkedHashMap()

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

                                    accountLedgerGist.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText]!!.finalBalanceOnDate =
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
                                            accountLedgerGist.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText] =
                                                AccountLedgerGistDateLedgerModel(
                                                    initialBalanceOnDate = initialBalanceOnDate,
                                                    transactionsOnDate = mutableListOf()
                                                )
                                        } else {

                                            accountLedgerGist.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText] =
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
                                        accountLedgerGist.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText]!!.transactionsOnDate.add(

                                            AccountLedgerGistTransactionModel(

                                                transactionParticulars = transactionParticulars,
                                                transactionAmount = transactionAmount
                                            )
                                        )

                                        if (isFinalBalanceWritten && (finalBalanceWrittenDate == previousDate)) {

                                            accountLedgerGist.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText]!!.finalBalanceOnDate =
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
                        accountLedgerGist.accountLedgerPages.forEach { accountLedgerGistIdPage: Map.Entry<UInt, LinkedHashMap<String, AccountLedgerGistDateLedgerModel>> ->

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
        return accountLedgerGist
    }

    fun processGistIdForTextData(

        userName: String,
        userId: UInt,
        gitHubAccessToken: String,
        gistId: String,
        isDevelopmentMode: Boolean,
        isApiCall: Boolean = true,
        isVersion3: Boolean = false

    ): String {

        val accountLedgerGist: AccountLedgerGistModel = processGistIdForData(

            userName = userName,
            userId = userId,
            gitHubAccessToken = gitHubAccessToken,
            gistId = gistId,
            isDevelopmentMode = isDevelopmentMode,
            isApiCall = isApiCall,
            isVersion3 = isVersion3
        )
        if (isDevelopmentMode) {

            println(
                "Gist Data : ${

                    Json.encodeToString(

                        serializer = AccountLedgerGistModel.serializer(),
                        value = accountLedgerGist
                    )
                }"
            )
        }

        val accountLedgerGistV2 = AccountLedgerGistModelV2(

            userName = accountLedgerGist.userName,
            userId = userId,
            accountLedgerPages = mutableListOf()
        )

        accountLedgerGist.accountLedgerPages.forEach { accountLedgerPage: Map.Entry<UInt, LinkedHashMap<String, AccountLedgerGistDateLedgerModel>> ->

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

            accountLedgerGistV2.accountLedgerPages.add(localAccountLedgerPage)
        }

        val accountLedgerGistTextV2: String = Json.encodeToString(

            serializer = AccountLedgerGistModelV2.serializer(),
            value = accountLedgerGistV2
        )
        if (isDevelopmentMode) {

            println("Gist Data ${if (isVersion3) "V3" else "V2"} : $accountLedgerGistTextV2")
        }

        return accountLedgerGistTextV2
    }
}
