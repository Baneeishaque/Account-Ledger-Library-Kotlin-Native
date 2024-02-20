package account_ledger_library.models

import kotlinx.serialization.Serializable

@Serializable
data class TransactionModel(

    val fromAccountId: UInt,
    val toAccountId: UInt,
    val eventDateTimeInText: String,
    val particulars: String,
    val amount: Float
)