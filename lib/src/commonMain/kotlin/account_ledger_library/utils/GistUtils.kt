package account_ledger_library.utils

import account_ledger_library.constants.ConstantsNative
import account_ledger_library.models.*
import common.utils.library.utils.CommonGistUtils
import common.utils.library.utils.DateTimeUtils
import common.utils.library.utils.KeyListUtils
import io.ktor.utils.io.core.*
import korlibs.time.Date
import korlibs.time.DateTime
import korlibs.time.parseDate
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmStatic

object GistUtils {

    @JvmStatic
    fun processGistIdForData(

        userName: String,
        userId: UInt,
        gitHubAccessToken: String,
        gistId: String,
        isDevelopmentMode: Boolean,
        isApiCall: Boolean = true

    ): AccountLedgerGistModel {

        val accountLedgerGist =
            AccountLedgerGistModel(userName = userName, userId = userId, accountLedgerPages = LinkedHashMap())
        runBlocking {

            CommonGistUtils.getHttpClientForGitHub(

                accessToken = gitHubAccessToken,
                isDevelopmentMode = isDevelopmentMode

            ).use { client ->

                // TODO: inline use of serialization : for file name in gist
                val gistContent: String = CommonGistUtils.getGistContents(

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
                            println("Current line = $currentLine")
                            println("currentAccountId = $currentAccountId")
                        }

                        if (currentLine.contains(ConstantsNative.accountIdPrefix)) {
                            currentAccountId =
                                currentLine.substring(
                                    currentLine.indexOf(ConstantsNative.accountIdPrefix) + 1, currentLine.indexOf(
                                        ConstantsNative.accountIdSuffix
                                    )
                                ).toUInt()
                            if (currentAccountId == 0U) {

                                val errorMessage =
                                    "Error : Account ID Must be a positive number, please correct it in your Gist Text for A/C {${
                                        currentLine.substring(
                                            0, currentLine.indexOf(
                                                ConstantsNative.accountIdPrefix
                                            )
                                        ).trim()
                                    }}"

                                if (isApiCall) {
                                    //TODO : Create Common Utils for Multiplatform,
                                    // Depend on it to use ApiErrorMessage function
                                    if (isDevelopmentMode) {
                                        println(errorMessage)
                                    }
                                } else {
                                    println(errorMessage)
                                }
                                isExecutionSuccess = false

                            } else {
                                extractedLedger = KeyListUtils.addElementToKeyListPair(
                                    keyList = extractedLedger,
                                    desiredKey = currentAccountId,
                                    desiredElement = currentLine.substring(currentLine.indexOf(ConstantsNative.accountIdSuffix) + 2)
                                )
                            }
                        } else {

                            if (currentLine.isNotEmpty()) {
                                extractedLedger = KeyListUtils.addElementToKeyListPair(
                                    keyList = extractedLedger,
                                    desiredKey = currentAccountId,
                                    desiredElement = currentLine
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

                            if (ledgerLine.first() != ConstantsNative.dateUnderlineCharacter) {

                                if (ledgerLine.first() == ConstantsNative.finalBalancePrefixCharacter) {

                                    isNextLineFinalBalance = true

                                } else if (isNextLineFinalBalance) {

                                    if (isDevelopmentMode) {
                                        println("ledgerLine = $ledgerLine")
                                        if (ledgerLine.trim().contains(char = ' ')) {
                                            val endIndex = ledgerLine.indexOf(char = ' ')
                                            println("endIndex = $endIndex")
                                            println("actual value : ${ledgerLine.substring(0, endIndex)}")
                                        }
                                    }

                                    val finalBalance: Double = (
                                            if (ledgerLine.trim().contains(char = ' '))
                                                ledgerLine.substring(0, ledgerLine.indexOf(char = ' '))
                                            else ledgerLine).toDouble()
                                    val transactionDateAsText: String =
                                        previousDate.format(DateTimeUtils.normalDatePattern)

                                    accountLedgerGist.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText]!!.finalBalanceOnDate =
                                        finalBalance
                                    isFinalBalanceWritten = true
                                    finalBalanceWrittenDate = previousDate

                                    isNextLineFinalBalance = false

                                } else {

                                    val ledgerLineContents: List<String> = ledgerLine.split(" ", limit = 2)
                                    val dateOrAmount: String = ledgerLineContents.first()
                                    try {
                                        val transactionDate =
                                            DateTimeUtils.normalDatePattern.parseDate(str = dateOrAmount)
                                        val transactionDateAsText: String =
                                            transactionDate.format(DateTimeUtils.normalDatePattern)
                                        if (ledgerLineContents.size > 1) {

                                            val initialBalanceOnDate: Double = ledgerLineContents[1].toDouble()
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

                                        val transactionAmount: Double =
                                            if (dateOrAmount == "0") 0.0 else (if (dateOrAmount.contains(char = '+')) dateOrAmount.toDouble() else (if (dateOrAmount.contains(
                                                    char = '-'
                                                )
                                            ) dateOrAmount.toDouble() else -(dateOrAmount.toDouble())))
                                        val transactionParticulars: String = ledgerLineContents[1]

                                        val transactionDateAsText: String =
                                            previousDate.format(DateTimeUtils.normalDatePattern)

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

    @JvmStatic
    fun processGistIdForTextData(

        userName: String,
        userId: UInt,
        gitHubAccessToken: String,
        gistId: String,
        isDevelopmentMode: Boolean,
        isApiCall: Boolean = true

    ): String {

        val accountLedgerGist = processGistIdForData(
            userName = userName,
            userId = userId,
            gitHubAccessToken = gitHubAccessToken,
            gistId = gistId,
            isDevelopmentMode = isDevelopmentMode,
            isApiCall = isApiCall,
        )
        if (isDevelopmentMode) {

            println(
                "Gist Data : ${
                    Json.encodeToString(
                        AccountLedgerGistModel.serializer(),
                        accountLedgerGist
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
            AccountLedgerGistModelV2.serializer(),
            accountLedgerGistV2
        )
        if (isDevelopmentMode) {

            println("Gist Data V2 : $accountLedgerGistTextV2")
        }

        return accountLedgerGistTextV2
    }
}
