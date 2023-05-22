package account_ledger_library.utils

import account_ledger_library.constants.Constants
import account_ledger_library.constants.Constants.accountIdPrefix
import account_ledger_library.constants.Constants.accountIdSuffix
import account_ledger_library.models.*
import com.soywiz.klock.Date
import com.soywiz.klock.DateTime
import com.soywiz.klock.parseDate
import common_utils_library.utils.CommonGistUtils
import common_utils_library.utils.DateTimeUtils
import io.ktor.utils.io.core.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class GistUtils {

    fun processGistIdForData(

        userName: String,
        gitHubAccessToken: String,
        gistId: String,
        isDevelopmentMode: Boolean,
        isApiCall: Boolean = true

    ): AccountLedgerGistModel {

        val accountLedgerGist = AccountLedgerGistModel(userName = userName, accountLedgerPages = LinkedHashMap())
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
//                            println("Current line = $currentLine")
//                            println("currentAccountId = $currentAccountId")
                        }

                        if (currentLine.contains(accountIdPrefix)) {
                            currentAccountId =
                                currentLine.substring(
                                    currentLine.indexOf(accountIdPrefix) + 1, currentLine.indexOf(
                                        accountIdSuffix
                                    )
                                ).toUInt()
                            if (currentAccountId == 0U) {

                                val errorMessage =
                                    "Error : Account ID Must be a positive number, please correct it in your Gist Text for A/C {${
                                        currentLine.substring(
                                            0, currentLine.indexOf(
                                                accountIdPrefix
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
                                extractedLedger = TextAccountLedgerUtils.addLineToCurrentAccountLedger(
                                    ledgerToProcess = extractedLedger,
                                    desiredAccountId = currentAccountId,
                                    desiredLine = currentLine.substring(currentLine.indexOf(accountIdSuffix) + 2)
                                )
                            }
                        } else {

                            if (currentLine.isNotEmpty()) {
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

                        if (isDevelopmentMode && (localCurrentAccountId == 8809U)) {
                            println()
                        }

                        var isNextLineFinalBalance = false
                        var previousDate: Date = DateTime.now().date

                        currentAccountLedgerLines.forEach { ledgerLine: String ->

                            if (ledgerLine.first() != Constants.dateUnderlineCharacter) {

                                if (ledgerLine.first() == Constants.finalBalancePrefixCharacter) {

                                    isNextLineFinalBalance = true

                                } else if (isNextLineFinalBalance) {

                                    val finalBalance: Double = ledgerLine.trim().toDouble()
                                    val transactionDateAsText: String =
                                        previousDate.format(DateTimeUtils.normalDatePattern)

                                    if (isDevelopmentMode && (finalBalance == 3.52)) {
                                        println()
                                    }

                                    accountLedgerGist.accountLedgerPages[localCurrentAccountId]!![transactionDateAsText]!!.finalBalanceOnDate =
                                        finalBalance

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
                                        if (isDevelopmentMode) {
                                            println()
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
        gitHubAccessToken: String,
        gistId: String,
        isDevelopmentMode: Boolean,
        isApiCall: Boolean = true

    ): String {

        val accountLedgerGist =
            processGistIdForData(userName, gitHubAccessToken, gistId, isDevelopmentMode, isApiCall)
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
